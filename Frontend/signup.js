// signup.js
// Lightweight UI form handler (replace with API call later)
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('signupForm');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    e.preventDefault();

    const name = document.getElementById('signupName')?.value?.trim();
    const email = document.getElementById('signupEmail')?.value?.trim();
    const password = document.getElementById('signupPassword')?.value?.trim();

    // Basic validation
    if (!name || !email || !password) {
      alert('Please fill all fields 💗');
      return;
    }

    // TODO: Replace with POST to backend register endpoint
    // fetch('/api/auth/register', { method: 'POST', body: JSON.stringify({name,email,password}) ... })

    // For now: demo flow
    alert('Account created (UI demo). You will be redirected to the quiz.');
    window.location.href = 'personality-quiz.html';
  });
});
