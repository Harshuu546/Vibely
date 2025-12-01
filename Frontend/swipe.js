// =======================
// GLOBAL VARIABLES
// =======================
let matches = [];
let currentIndex = 0;

const FALLBACK_SVG = `data:image/svg+xml;utf8,
<svg xmlns='http://www.w3.org/2000/svg' width='800' height='1000' viewBox='0 0 800 1000'>
  <rect width='100%' height='100%' fill='%23f3f0ff'/>
  <text x='50%' y='48%' dominant-baseline='middle' text-anchor='middle'
        font-size='36' fill='%23707090' font-family='Arial,Helvetica,sans-serif'>
    Image unavailable
  </text>
</svg>`;

// =======================
// LOAD MATCH DATA
// =======================
async function loadMatches() {
  try {
    const res = await fetch("mock-matches.json");
    if (!res.ok) throw new Error("Failed to load JSON");
    matches = await res.json();
  } catch (err) {
    console.error("JSON load error:", err);

    matches = [
      {
        name: "Sample User",
        age: 22,
        city: "Test City",
        budget: "10k",
        roomType: "Shared",
        bio: "Fallback profile",
        photo: "",
        tags: ["Test", "Sample"]
      }
    ];
  }

  renderCard();
}

// =======================
// COMPATIBILITY SCORE
// =======================
function calculateMatchScore(user) {
  let score = 0;
  let total = 5;

  const me = JSON.parse(localStorage.getItem("vibely_profile_full") || "{}");
  if (!me) return 0;

  if (me.city === user.city) score++;
  if (me.budget === user.budget) score++;
  if (me.roomType === user.roomType) score++;
  if (me.personality === user.personality) score++;
  if (me.pets === user.pets) score++;

  return Math.round((score / total) * 100);
}

// =======================
// RENDER CARD
// =======================
function renderCard() {
  const container = document.getElementById("cardContainer");
  container.innerHTML = "";

  // === INFINITE LOOP FIX ===
  if (currentIndex >= matches.length) {
    currentIndex = 0; // restart swipe from first profile
  }

  const user = matches[currentIndex];
  const matchScore = calculateMatchScore(user);

  let badgeColor =
    matchScore >= 80 ? "#79d19a" :
    matchScore >= 60 ? "#ffd66e" :
    matchScore >= 40 ? "#ff9e7a" :
                       "#ff6e84";

  const card = document.createElement("div");
  card.className = "swipe-card";

  const img = document.createElement("img");
  img.className = "card-photo";
  img.src = user.photo || FALLBACK_SVG;
  img.onerror = () => (img.src = FALLBACK_SVG);

  const info = document.createElement("div");
  info.className = "card-info";

  info.innerHTML = `
    <div class="match-badge" style="background:${badgeColor}">
      ${matchScore}% Match
    </div>

    <h2>${user.name}</h2>
    <p class="age">${user.age} · ${user.city}</p>

    <div class="details">
      <strong>Budget:</strong> ${user.budget}<br>
      <strong>Room Type:</strong> ${user.roomType}<br>
      <strong>Bio:</strong> ${user.bio}
    </div>

    <div class="tags">
      ${(user.tags || []).map(t => `<span class="tag">${t}</span>`).join("")}
    </div>
  `;

  card.appendChild(img);
  card.appendChild(info);
  container.appendChild(card);
}

// =======================
// SWIPE EFFECT
// =======================
function animateSwipe(type) {
  const card = document.querySelector(".swipe-card");
  if (!card) return;

  if (type === "like") {
    const user = matches[currentIndex];
    user.matchScore = calculateMatchScore(user);

    let liked = JSON.parse(localStorage.getItem("vibely_likes") || "[]");
    liked.push(user);
    localStorage.setItem("vibely_likes", JSON.stringify(liked));

    card.classList.add("like-anim");
  }

  if (type === "skip") {
    card.classList.add("skip-anim");
  }

  setTimeout(() => {
    currentIndex++;
    renderCard();
  }, 300);
}

// =======================
// BUTTON EVENTS
// =======================
document.getElementById("skipBtn").addEventListener("click", () => {
  animateSwipe("skip");
});

document.getElementById("likeBtn").addEventListener("click", () => {
  animateSwipe("like");
});

// =======================
// START APP
// =======================
loadMatches();
