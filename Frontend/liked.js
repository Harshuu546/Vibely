let liked = JSON.parse(localStorage.getItem("vibely_likes") || "[]");

const container = document.getElementById("likedContainer");

function renderLiked() {
    container.innerHTML = "";

    if (liked.length === 0) {
        container.innerHTML = `<p class="empty">You haven't liked anyone yet 💔</p>`;
        return;
    }

    liked.forEach((profile, index) => {
        const card = document.createElement("div");
        card.className = "card";

        card.innerHTML = `
            <img src="${profile.photo}" alt="${profile.name}">

            <div class="info">
                <div class="name">${profile.name}</div>
                <div class="meta">${profile.age} · ${profile.city}</div>

                <div class="tags">
                    ${profile.tags.map(t => `<span class="tag">${t}</span>`).join("")}
                </div>

                <button class="remove-btn" onclick="removeLiked(${index})">
                    Remove
                </button>
            </div>
        `;

        container.appendChild(card);
    });
}

function removeLiked(i) {
    liked.splice(i, 1);
    localStorage.setItem("vibely_likes", JSON.stringify(liked));
    renderLiked();
}

renderLiked();
