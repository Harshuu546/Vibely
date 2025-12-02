// Load liked profiles as your "chat people"
let chats = JSON.parse(localStorage.getItem("vibely_likes") || "[]");

const list = document.getElementById("chatList");

function loadChats() {
    if (chats.length === 0) {
        list.innerHTML = `<p style="text-align:center;opacity:0.7;">No chats yet 💭</p>`;
        return;
    }

    chats.forEach((user, index) => {
        const item = document.createElement("div");
        item.className = "chat-item";

        item.innerHTML = `
            <img src="${user.photo}" alt="Profile">

            <div class="chat-info">
                <div class="chat-name">${user.name}</div>
                <div class="chat-preview">${user.lastMessage || "Say hello 👋"}</div>
            </div>

            ${user.unread ? `<div class='unread-dot'></div>` : ""}
        `;

        item.onclick = () => {
            window.location.href = `chat.html?id=${index}`;
        };

        list.appendChild(item);
    });
}

loadChats();
