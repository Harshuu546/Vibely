// Load final profile
const data = JSON.parse(localStorage.getItem('vibely_profile_full'));

if (!data) {
  alert("No profile found. Please complete profile setup.");
  window.location.href = "profile-setup.html";
}

// Fill photo
const photoBox = document.getElementById("photoPreview");
if (data.photoDataUrl) {
  photoBox.innerHTML = "";
  const img = document.createElement("img");
  img.src = data.photoDataUrl;
  photoBox.appendChild(img);
}

// Fill text fields
document.getElementById("pvName").textContent = data.fullName || "—";
document.getElementById("pvAge").textContent = data.age || "—";
document.getElementById("pvGender").textContent = data.gender || "—";
document.getElementById("pvBio").textContent = data.bio || "—";

document.getElementById("pvCity").textContent = data.city || "—";
document.getElementById("pvBudget").textContent = data.budget || "—";
document.getElementById("pvRoomType").textContent = data.roomType || "—";
document.getElementById("pvMoveIn").textContent = data.moveIn || "—";
document.getElementById("pvFurnished").textContent = data.furnished || "—";
document.getElementById("pvNeedMaid").textContent = data.needMaid || "—";

document.getElementById("pvPets").textContent = data.pets || "—";
document.getElementById("pvSmoking").textContent = data.smoking || "—";
document.getElementById("pvGuests").textContent = data.guests || "—";
document.getElementById("pvCleaning").textContent = data.cleaning || "—";
document.getElementById("pvWorkPattern").textContent = data.workPattern || "—";

// Buttons
document.getElementById("editBtn").addEventListener("click", () => {
  window.location.href = "profile-setup.html";
});

document.getElementById("continueBtn").addEventListener("click", () => {
  window.location.href = "dashboard.html"; // next page
});
