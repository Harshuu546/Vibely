// onboarding-step2.js
// Handles Get Started / Next on onboarding-step2.html
document.addEventListener('DOMContentLoaded', () => {
  const next = document.getElementById('onboard2Next');
  if (next) {
    next.addEventListener('click', () => {
      window.location.href = 'login.html';
    });
  }
});
