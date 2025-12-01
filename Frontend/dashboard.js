// Load profile from localStorage
const data = JSON.parse(localStorage.getItem('vibely_profile_full'));

if (!data) {
  alert("No profile found. Please complete your profile.");
  window.location.href = "profile-setup.html";
}

// Set name
document.getElementById("dashName").textContent = data.fullName || "User";

// Fill profile info
document.getElementById("dashCity").textContent = data.city || "—";
document.getElementById("dashBudget").textContent = data.budget || "—";
document.getElementById("dashRoomType").textContent = data.roomType || "—";

// Fill Photo
const photoBox = document.getElementById("dashPhoto");
if (data.photoDataUrl) {
  photoBox.innerHTML = "";
  const img = document.createElement("img");
  img.src = data.photoDataUrl;
  photoBox.appendChild(img);
}

// Edit profile
document.getElementById("editProfileBtn").addEventListener("click", () => {
  window.location.href = "profile-setup.html";
});

// View quiz summary
document.getElementById("viewQuizBtn").addEventListener("click", () => {
  window.location.href = "quiz-summary.html"; 
});

// ⭐ Find matches → swipe UI
document.getElementById("swipeBtn").addEventListener("click", () => {
  window.location.href = "swipe.html";
});
