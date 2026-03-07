/**
 * Safe View Switcher
 * Prevents "null" errors if the element doesn't exist on the current page
 */
function showView(viewId) {
    const views = document.querySelectorAll('.view-content');

    // If no views exist on this page (like a simple static page),
    // we stop execution to prevent errors.
    if (views.length === 0) return;

    // Hide all views
    views.forEach(view => view.classList.remove('active'));

    // Show target view
    const target = document.getElementById(viewId);
    if (target) {
        target.classList.add('active');
        updateActiveLinkStyles(viewId);
    } else {
        // Fallback to first view if target is missing
        views[0].classList.add('active');
    }

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function updateActiveLinkStyles(viewId) {
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
        const text = link.innerText.toLowerCase();

        // Logical matching
        if (viewId === 'dashboard' && text.includes('dashboard')) link.classList.add('active');
        if (viewId === 'data' && text.includes('records')) link.classList.add('active');
        if (viewId === 'settings' && text.includes('config')) link.classList.add('active');
    });
}
function openChat(name, avatar) {
    activeName.innerText = name;
    headerName.innerText = name;
    activeAvatar.src = avatar;
    headerAvatar.src = avatar;
    
    // Clear old messages except the header and timestamp
    const messages = messageDisplay.querySelectorAll('.message-in, .flex-col.items-end');
    messages.forEach(m => m.remove());

    // Mock specific conversation data
    const mockHistory = {
        'Alex Rivera': ["Hey! The updates look incredible.", "Are we still on for the demo at 4?"],
        'Jordan Smith': ["I've uploaded the PDF for the contract.", "Let me know when you've signed it."],
        'Sarah Chen': ["Don't forget the cake for the office party!", "I'll be there in 10 mins."]
    };

    const history = mockHistory[name] || ["Hello! How can I help you today?"];
    
    history.forEach(msg => {
        const html = `
            <div class="flex items-start max-w-[85%] message-in">
                <div class="ai-bubble p-4 px-5 text-sm leading-relaxed shadow-sm">${msg}</div>
            </div>`;
        messageDisplay.insertAdjacentHTML('beforeend', html);
    });

    listView.classList.add('hidden');
    conversationView.classList.remove('hidden');
    conversationView.classList.add('flex');
    
    setTimeout(() => { messageDisplay.scrollTop = messageDisplay.scrollHeight; }, 50);
}