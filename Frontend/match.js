// Get ID from URL
const id = new URLSearchParams(window.location.search).get("id");

// Load liked profiles
const liked = JSON.parse(localStorage.getItem("vibely_likes") || "[]");

// Selected profile
const user = liked[id];

if (!user) {
    document.body.innerHTML = "<h2 style='text-align:center;margin-top:50px;'>Match not found 💔</h2>";
} else {
    document.getElementById("userPhoto").src = JSON.parse(localStorage.getItem("vibely_profile")).photo || "../assets/default.png";
    document.getElementById("matchPhoto").src = user.photo;
    document.getElementById("matchName").innerText = user.name;

    document.getElementById("viewProfileBtn").onclick = () => {
        window.location.href = `profile.html?id=${id}`;
    };
}

// ==========================
// Confetti Animation
// ==========================
const confettiCanvas = document.getElementById("confetti");
const ctx = confettiCanvas.getContext("2d");

let confettis = [];

function resizeCanvas() {
    confettiCanvas.width = window.innerWidth;
    confettiCanvas.height = window.innerHeight;
}

resizeCanvas();
window.addEventListener("resize", resizeCanvas);

function Confetti() {
    this.x = Math.random() * confettiCanvas.width;
    this.y = Math.random() * confettiCanvas.height - confettiCanvas.height;
    this.size = Math.random() * 6 + 4;
    this.speedY = Math.random() * 3 + 2;
    this.color = `hsl(${Math.random() * 360}, 80%, 70%)`;
}

function animate() {
    ctx.clearRect(0, 0, confettiCanvas.width, confettiCanvas.height);

    confettis.forEach(c => {
        c.y += c.speedY;

        ctx.fillStyle = c.color;
        ctx.fillRect(c.x, c.y, c.size, c.size);

        if (c.y > confettiCanvas.height) {
            c.y = -10;
        }
    });

    requestAnimationFrame(animate);
}

function createConfetti() {
    for (let i = 0; i < 200; i++) {
        confettis.push(new Confetti());
    }
}

createConfetti();
animate();
