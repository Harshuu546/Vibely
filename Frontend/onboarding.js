// onboarding.js
// Handles Next button on onboarding.html
document.addEventListener('DOMContentLoaded', () => {
  const next = document.getElementById('onboardNext');
  if (next) {
    next.addEventListener('click', () => {
      window.location.href = 'onboarding-step2.html';
    });
  }
});
