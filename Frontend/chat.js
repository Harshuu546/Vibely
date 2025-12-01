// Get chat id from URL
const id = new URLSearchParams(window.location.search).get("id");

let chats = JSON.parse(localStorage.getItem("vibely_likes") || "[]");

// If invalid id
if (!chats[id]) {
    document.body.innerHTML = "<h2 style='text-align:center;margin-top:40px;'>Chat not found 💔</h2>";
    throw new Error("Chat not found");
}

const user = chats[id];

// Header info
document.getElementById("userPhoto").src = user.photo;
document.getElementById("userName").innerText = user.name;

// Load messages for this chat
let messages = JSON.parse(localStorage.getItem("chat_" + id) || "[]");

const msgBox = document.getElementById("messages");

// Render messages
function renderMessages() {
    msgBox.innerHTML = "";

    messages.forEach(msg => {
        const div = document.createElement("div");
        div.className = "message " + (msg.from === "me" ? "me" : "them");
        div.innerText = msg.text;
        msgBox.appendChild(div);
    });

    msgBox.scrollTop = msgBox.scrollHeight;
}

renderMessages();

// Send message
document.getElementById("sendBtn").addEventListener("click", sendMessage);
document.getElementById("chatInput").addEventListener("keypress", e => {
    if (e.key === "Enter") sendMessage();
});

function sendMessage() {
    const input = document.getElementById("chatInput");
    let text = input.value.trim();

    if (text === "") return;

    const msg = { from: "me", text };

    messages.push(msg);
    localStorage.setItem("chat_" + id, JSON.stringify(messages));

    input.value = "";
    renderMessages();

    // optional fake reply for testing
    setTimeout(() => {
        const reply = {
            from: "them",
            text: "That's cool! 😊"
        };

        messages.push(reply);
        localStorage.setItem("chat_" + id, JSON.stringify(messages));
        renderMessages();
    }, 900);
}
