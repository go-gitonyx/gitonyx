const API = `${location.protocol}//${location.hostname}:3493`;

async function handleLogin(e) {
    e.preventDefault();
    const form = document.getElementById('loginForm');
    const error = document.getElementById('loginError');

    const login = form.querySelector('[name="login"]').value.trim();
    const password = form.querySelector('[name="password"]').value;

    error.classList.add('hidden');

    try {
        const res = await fetch(`${API}/api/v1/user/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ login, password }),
        });
        const data = await res.json();
        if (!res.ok) {
            showError(error, data.error || 'Login failed');
            return;
        }
        window.location.href = '/dashboard';
    } catch {
        showError(error, 'Could not reach the server');
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const form = document.getElementById('registerForm');
    const error = document.getElementById('registerError');

    const firstName = form.querySelector('[name="firstName"]').value.trim();
    const lastName  = form.querySelector('[name="lastName"]').value.trim();
    const username  = form.querySelector('[name="username"]').value.trim();
    const email     = form.querySelector('[name="email"]').value.trim();
    const password  = form.querySelector('[name="password"]').value;

    error.classList.add('hidden');

    try {
        const res = await fetch(`${API}/api/v1/user/register`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ firstName, lastName, username, email, password }),
        });
        const data = await res.json();
        if (!res.ok) {
            showError(error, data.error || 'Registration failed');
            return;
        }
        window.location.href = '/login';
    } catch {
        showError(error, 'Could not reach the server');
    }
}

function showError(el, message) {
    el.textContent = message;
    el.classList.remove('hidden');
}
