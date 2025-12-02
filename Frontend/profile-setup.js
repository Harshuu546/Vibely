// profile-setup.js
// Multi-step profile form: autosave to localStorage, image preview, validation, final save

(function () {
  // element refs
  const form = document.getElementById('profileForm');
  const steps = Array.from(document.querySelectorAll('.form-step'));
  const stepIndicator = document.getElementById('stepIndicator');
  const backBtn = document.getElementById('backBtn');
  const nextBtn = document.getElementById('nextBtn');
  const saveDraftBtn = document.getElementById('saveDraft');

  const photoInput = document.getElementById('photoInput');
  const photoPreview = document.getElementById('photoPreview');

  // localStorage keys
  const DRAFT_KEY = 'vibely_profile_draft';
  const FINAL_KEY = 'vibely_profile_full';

  let stepIndex = 0;
  let draft = loadDraft() || {};

  // populate inputs from draft if present
  function populateFromDraft() {
    const map = {
      fullName: 'fullName',
      age: 'age',
      gender: 'gender',
      bio: 'bio',
      city: 'city',
      moveIn: 'moveIn',
      budget: 'budget',
      roomType: 'roomType',
      furnished: 'furnished',
      needMaid: 'needMaid',
      pets: 'pets',
      smoking: 'smoking',
      guests: 'guests',
      cleaning: 'cleaning',
      workPattern: 'workPattern'
    };
    Object.keys(map).forEach(k => {
      const el = document.getElementById(k);
      if (el && draft[map[k]] !== undefined) el.value = draft[map[k]];
    });

    // photo
    if (draft.photoDataUrl) {
      showPhotoPreview(draft.photoDataUrl);
    }
  }

  function loadDraft() {
    try {
      const raw = localStorage.getItem(DRAFT_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch (e) { return null; }
  }

  function saveDraft() {
    // read all fields and save partial
    const data = {
      fullName: (document.getElementById('fullName')?.value || '').trim(),
      age: (document.getElementById('age')?.value || '').trim(),
      gender: (document.getElementById('gender')?.value || '').trim(),
      bio: (document.getElementById('bio')?.value || '').trim(),
      city: (document.getElementById('city')?.value || '').trim(),
      moveIn: (document.getElementById('moveIn')?.value || '').trim(),
      budget: (document.getElementById('budget')?.value || '').trim(),
      roomType: (document.getElementById('roomType')?.value || '').trim(),
      furnished: (document.getElementById('furnished')?.value || '').trim(),
      needMaid: (document.getElementById('needMaid')?.value || '').trim(),
      pets: (document.getElementById('pets')?.value || '').trim(),
      smoking: (document.getElementById('smoking')?.value || '').trim(),
      guests: (document.getElementById('guests')?.value || '').trim(),
      cleaning: (document.getElementById('cleaning')?.value || '').trim(),
      workPattern: (document.getElementById('workPattern')?.value || '').trim(),
      photoDataUrl: draft.photoDataUrl || null
    };

    draft = data;
    localStorage.setItem(DRAFT_KEY, JSON.stringify(data));
  }

  // photo preview helpers
  function showPhotoPreview(dataUrl) {
    photoPreview.innerHTML = '';
    const img = document.createElement('img');
    img.src = dataUrl;
    img.alt = 'Profile photo';
    photoPreview.appendChild(img);
  }

  photoInput.addEventListener('change', (e) => {
    const file = e.target.files && e.target.files[0];
    if (!file) return;
    // preview and store dataURL
    const reader = new FileReader();
    reader.onload = function (ev) {
      const dataUrl = ev.target.result;
      draft.photoDataUrl = dataUrl;
      showPhotoPreview(dataUrl);
      localStorage.setItem(DRAFT_KEY, JSON.stringify(draft));
    };
    reader.readAsDataURL(file);
  });

  // auto-save on inputs
  form.addEventListener('input', () => {
    saveDraft();
  });

  // navigation
  function showStep(i) {
    steps.forEach((s, idx) => {
      s.hidden = idx !== i;
    });
    stepIndex = i;
    stepIndicator.textContent = `Step ${i+1} / ${steps.length}`;
    backBtn.disabled = i === 0;
    nextBtn.textContent = i === steps.length - 1 ? 'Finish' : 'Next';
  }

  backBtn.addEventListener('click', () => {
    if (stepIndex > 0) {
      showStep(stepIndex - 1);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  });

  nextBtn.addEventListener('click', () => {
    // validate current step
    if (!validateStep(stepIndex)) return;

    if (stepIndex < steps.length - 1) {
      showStep(stepIndex + 1);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } else {
      // final: combine and save
      saveDraft(); // ensure latest saved
      const finalProfile = {
        ...draft,
        createdAt: new Date().toISOString()
      };
      localStorage.setItem(FINAL_KEY, JSON.stringify(finalProfile));
      // optionally clear draft:
      localStorage.removeItem(DRAFT_KEY);

      alert('Profile saved! Proceeding to profile preview.');
      window.location.href = 'profile-preview.html'; // change as needed
    }
  });

  saveDraftBtn.addEventListener('click', () => {
    saveDraft();
    alert('Draft saved locally.');
  });

  function validateStep(i) {
    // Step-specific required fields:
    if (i === 0) {
      const name = document.getElementById('fullName')?.value?.trim();
      if (!name) {
        alert('Please enter your full name.');
        return false;
      }
    } else if (i === 1) {
      const city = document.getElementById('city')?.value?.trim();
      const budget = document.getElementById('budget')?.value?.trim();
      const roomType = document.getElementById('roomType')?.value?.trim();
      if (!city) { alert('Please enter your city/area.'); return false; }
      if (!budget) { alert('Please select your budget range.'); return false; }
      if (!roomType) { alert('Please choose your room preference.'); return false; }
    }
    // other steps no hard validation
    return true;
  }

  // init
  populateFromDraft();
  showStep(0);

})();
