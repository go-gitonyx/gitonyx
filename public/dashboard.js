const API = `${location.protocol}//${location.hostname}:3493`;

(function () {
    if (!getCookie('access_token')) {
        window.location.replace('/login');
        return;
    }

    const raw = getCookie('session_info');
    if (raw) {
        try {
            const user = JSON.parse(decodeURIComponent(raw));
            document.addEventListener('DOMContentLoaded', () => {
                const greeting = document.getElementById('dashGreeting');
                if (greeting) greeting.textContent = `Welcome back, ${user.firstName || user.username}`;
            });
        } catch (_) {}
    }
})();

async function logout() {
    await fetch(`${API}/api/v1/user/logout`, {
        method: 'POST',
        credentials: 'include',
    }).catch(() => {});
    window.location.href = '/login';
}

function getCookie(name) {
    const match = document.cookie.split(';')
        .map(c => c.trim().split('='))
        .find(([k]) => k === name);
    return match ? match[1] : null;
}
