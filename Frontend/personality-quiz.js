// personality-quiz.js
// Quiz logic: render questions, next/prev, save answers to localStorage
(function () {
  // Questions array (28 questions)
  const questions = [
    { q: "How do you describe yourself?", options: ["Introvert","Ambivert","Extrovert"] },
    { q: "Do you prefer a calm or lively environment?", options: ["Calm","Balanced","Lively"] },
    { q: "How social are you?", options: ["Very social","Moderately social","Prefer minimal interaction"] },
    { q: "Do you party often?", options: ["Yes, every weekend","Sometimes","Rarely/Never"] },
    { q: "Are you a morning person or a night owl?", options: ["Early Bird","Night Owl","Varies"] },

    { q: "Do you smoke?", options: ["Yes","No","Occasionally"] },
    { q: "Do you drink alcohol?", options: ["Frequently","Sometimes","Never"] },
    { q: "Are you okay if your roommate smokes/drinks?", options: ["Yes","No","Depends"] },

    { q: "How tidy are you?", options: ["Very tidy","Moderate","Not tidy"] },
    { q: "How often do you clean your room?", options: ["Daily","Weekly","Rarely"] },
    { q: "How is your noise tolerance?", options: ["Low","Moderate","High"] },
    { q: "What time do you usually sleep?", options: ["Before 10 PM","10 PM - 1 AM","After 1 AM"] },
    { q: "What time do you usually wake up?", options: ["Before 7 AM","7 AM–10 AM","After 10 AM"] },

    { q: "Do you know how to cook?", options: ["Yes, well","Basic","No"] },
    { q: "How do you get most of your meals?", options: ["Cook myself","Order food","Eat outside"] },
    { q: "What type of food do you eat?", options: ["Veg","Non-Veg","Vegan"] },
    { q: "Kitchen usage preference?", options: ["Shared kitchen","Separate timings","Prefer minimal use"] },

    { q: "How often do your friends visit?", options: ["Frequently","Sometimes","Rarely"] },
    { q: "Are you comfortable with your roommate’s friends visiting?", options: ["Yes","Sometimes","Prefer rarely"] },
    { q: "Family visiting frequency?", options: ["Often","Rarely","Never"] },

    { q: "What is your monthly accommodation budget?", options: ["5k–10k","10k–15k","15k–20k","20k+"] },
    { q: "How do you prefer splitting expenses?", options: ["Equally","Usage-based","Flexible"] },
    { q: "Are you okay sharing utility bills?", options: ["Yes","No","Depends"] },
    { q: "Your total monthly spending capacity?", options: ["Under 10k","10k–20k","20k–30k","30k+"] },
    { q: "Do you need a maid?", options: ["Yes","No","Maybe"] },

    { q: "Do you prefer shared or separate rooms?", options: ["Shared room","Separate rooms"] },
    { q: "Are you okay with shared bathroom?", options: ["Yes","No","Prefer attached bathroom"] },
    { q: "Do you need a private study/work space?", options: ["Yes","No","Not necessary"] }
  ];

  const total = questions.length;
  let idx = 0;
  let answers = JSON.parse(localStorage.getItem('vibely_quiz')) || {};

  // DOM refs - these IDs must exist in the HTML
  const quizArea = document.getElementById('quizArea') || document.getElementById('question-card') || null;
  const prevBtn = document.getElementById('prevBtn') || document.getElementById('prev') || null;
  const nextBtn = document.getElementById('nextBtn') || document.getElementById('next') || null;
  const progressText = document.getElementById('progressText') || null;

  if (!quizArea) {
    console.error('Quiz container not found. Ensure your HTML has an element with id="quizArea" or id="question-card".');
    return;
  }

  function render() {
    // update progress if present
    if (progressText) progressText.textContent = `${idx+1} / ${total}`;

    const q = questions[idx];
    // build options
    const optionsHtml = q.options.map(opt => {
      const selected = answers[idx] === opt ? 'is-selected' : '';
      return `<button class="quiz-option ${selected}" type="button" data-value="${escapeAttr(opt)}">${escapeHtml(opt)}</button>`;
    }).join('');

    quizArea.innerHTML = `
      <div class="quiz-question">${escapeHtml(q.q)}</div>
      <div class="quiz-options">${optionsHtml}</div>
    `;

    // attach click handlers
    quizArea.querySelectorAll('.quiz-option').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const val = e.currentTarget.dataset.value;
        answers[idx] = val;
        localStorage.setItem('vibely_quiz', JSON.stringify(answers));
        // mark selected
        quizArea.querySelectorAll('.quiz-option').forEach(b => b.classList.remove('is-selected'));
        e.currentTarget.classList.add('is-selected');
      });
    });

    // disable prev button on first
    if (prevBtn) prevBtn.disabled = idx === 0;
    if (nextBtn) nextBtn.textContent = idx === total - 1 ? 'Finish' : 'Next';
  }

  function next() {
    if (!answers[idx]) { alert('Please select an option 💗'); return; }
    if (idx < total - 1) { idx++; render(); }
    else {
      // finished
      localStorage.setItem('vibely_quiz_final', JSON.stringify(answers));
      alert('Quiz completed! Your answers were saved locally (UI demo).');
      // Navigate to profile setup or wherever you want
      window.location.href = 'profile-setup.html';
    }
  }

  function prev() {
    if (idx > 0) { idx--; render(); }
  }

  // attach nav handlers if available
  if (prevBtn) prevBtn.addEventListener('click', prev);
  if (nextBtn) nextBtn.addEventListener('click', next);

  // helpers
  function escapeHtml(s){ return String(s).replace(/[&<>"']/g, (m)=>({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;' }[m])); }
  function escapeAttr(s){ return String(s).replace(/"/g, '&quot;'); }

  // initial render
  render();

})();
