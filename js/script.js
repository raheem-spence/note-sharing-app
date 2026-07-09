// Redirect to classes page
// Select button
const loginBtn = document.getElementById('login-btn');

// Check if element exists, meaning we are on that page
if (loginBtn) {
    // Add listener
    loginBtn.addEventListener('click', async (e) => {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        console.log("LOGIN CLICKED!");
        console.log("FETCH STARTED");

        try {
        const response = await fetch("http://127.0.0.1:8080/auth/login", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            }),
         
        });

        console.log("STATUS:", response.status);
        console.log("OK?:", response.ok);

        if (response.ok) {
            console.log("LOGIN SUCCESSFULL");
            window.location.href = "chemistry.html";
    
        } else {
            console.log("LOGIN FAILED :(");
        }
     } catch (err) {
            console.log("LOGIN ERROR:", err);
        }
    });
}
