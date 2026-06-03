// Redirect to classes page
// Select button
const loginBtn = document.getElementById('login-btn');

// Check if element exists, meaning we are on that page
if (loginBtn) {
    // Add listener
    loginBtn.addEventListener('click', async (e) => {
        e.preventDefault();

        const username = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        console.log("LOGIN CLICKED!");
        console.log("FETCH STARTED");

        try {
        const response = await fetch("http://localhost:8080/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            }),
            credentials: "include"
        });

        console.log("STATUS:", response.status);

        if (response.ok) {
            console.log("LOGIN SUCCESSFULL");

            setTimeout(() => {
                window.location.href = "classes.html";
            }, 300);
        } else {
            console.log("LOGIN FAILED :(");
        }
     } catch (err) {
            console.log("LOGIN ERROR:", err);
        }
    });
}
