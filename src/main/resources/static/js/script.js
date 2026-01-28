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