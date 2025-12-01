// Load profile for header info
let profile = JSON.parse(localStorage.getItem("vibely_profile") || "{}");

document.getElementById("profilePhoto").src = profile.photo || "user.png";
document.getElementById("profileName").innerText = profile.name || "User";
document.getElementById("profileCity").innerText = profile.city || "Unknown City";

// Navigation
function goTo(page) {
    window.location.href = page;
}

// Clear liked profiles
function clearLikes() {
    if (confirm("Are you sure you want to clear liked profiles? 💔")) {
        localStorage.removeItem("vibely_likes");
        alert("Liked profiles cleared.");
    }
}

// Logout
function logout() {
    if (confirm("Logout from Vibely?")) {
        localStorage.removeItem("loggedIn");
        window.location.href = "login.html";
    }
}

// Delete account
function deleteAccount() {
    if (confirm("Delete your account permanently? This cannot be undone. 😢")) {
        localStorage.removeItem("vibely_profile");
        localStorage.removeItem("vibely_likes");
        localStorage.removeItem("loggedIn");
        alert("Your account has been deleted.");
        window.location.href = "signup.html";
    }
}
