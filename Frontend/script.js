// splash.js
// Redirect to onboarding after a short delay
(function () {
  const DELAY_MS = 2200; // short delay so loader is visible
  setTimeout(() => {
    window.location.href = 'onboarding.html';
  }, DELAY_MS);
})();
