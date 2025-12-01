// Load existing profile
let profile = JSON.parse(localStorage.getItem("vibely_profile") || "{}");

// Fill fields with existing data
document.getElementById("name").value = profile.name || "";
document.getElementById("city").value = profile.city || "";
document.getElementById("budget").value = profile.budget || "10k";
document.getElementById("roomType").value = profile.roomType || "Shared";
document.getElementById("personality").value = profile.personality || "Ambivert";
document.getElementById("pets").value = profile.pets || "No";
document.getElementById("bio").value = profile.bio || "";

// Load photo
document.getElementById("profilePhoto").src =
    profile.photo || "user.png";

// Update photo when user selects new file
document.getElementById("photoInput").addEventListener("change", function() {
    const file = this.files[0];

    if (!file) return;

    const reader = new FileReader();
    reader.onload = function(e) {
        document.getElementById("profilePhoto").src = e.target.result;
        profile.photo = e.target.result;
    };
    reader.readAsDataURL(file);
});

// Save Form
document.getElementById("editForm").addEventListener("submit", function(e) {
    e.preventDefault();

    profile.name = document.getElementById("name").value;
    profile.city = document.getElementById("city").value;
    profile.budget = document.getElementById("budget").value;
    profile.roomType = document.getElementById("roomType").value;
    profile.personality = document.getElementById("personality").value;
    profile.pets = document.getElementById("pets").value;
    profile.bio = document.getElementById("bio").value;

    localStorage.setItem("vibely_profile", JSON.stringify(profile));

    alert("Profile updated successfully 💜");
    window.location.href = "profile-preview.html";
});
