// Read selected profile index from URL
const index = new URLSearchParams(window.location.search).get("id");

// Load liked profiles
let liked = JSON.parse(localStorage.getItem("vibely_likes") || "[]");

// Find the selected profile
const profile = liked[index];

if (!profile) {
    document.body.innerHTML = "<h2 style='text-align:center;margin-top:50px;'>Profile not found 💔</h2>";
} else {
    document.getElementById("profilePhoto").src = profile.photo;
    document.getElementById("profileName").innerText = profile.name;
    document.getElementById("profileMeta").innerText = `${profile.age} · ${profile.city}`;
    document.getElementById("bio").innerText = profile.bio;

    document.getElementById("budget").innerText = profile.budget;
    document.getElementById("roomType").innerText = profile.roomType;

    // Tags
    const tagBox = document.getElementById("tagContainer");
    tagBox.innerHTML = profile.tags.map(t => `<span class="tag">${t}</span>`).join("");
}
