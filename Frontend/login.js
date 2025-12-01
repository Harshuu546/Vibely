// login.js
// Lightweight UI form handler (replace with real auth calls later)
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    e.preventDefault();

    const email = document.getElementById('loginEmail')?.value?.trim();
    const password = document.getElementById('loginPassword')?.value?.trim();

    // Basic validation
    if (!email || !password) {
      alert('Please enter email and password 💗');
      return;
    }

    // TODO: Replace this with API call to your Spring backend
    // fetch('/api/auth/login', { method: 'POST', body: JSON.stringify({email, password})... })

    // For now: demo flow
    alert('Login successful (UI demo).');
    // Redirect to quiz or dashboard
    window.location.href = 'personality-quiz.html';
  });
});
