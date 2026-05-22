// Redirect to classes page
// Select button
const loginBtn = document.getElementById('login-btn');

// Check if element exists, meaning we are on that page
if (loginBtn) {
    // Add listener
    loginBtn.addEventListener('click', event => {
        window.location.href = "classes.html";
    })
}
