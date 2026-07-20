// Redirect to classes page
// Select button
const loginBtn = document.getElementById('login-btn');
const createAcctBtn = document.getElementById('create-accnt-btn');

// Check if element exists, meaning we are on that page
if (loginBtn) {
    // Add listener
    loginBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

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
            window.location.href = "course.html";
    
        } else {
            console.log("LOGIN FAILED :(");
        }
     } catch (err) {
            console.log("LOGIN ERROR:", err);
        }
    });
} else {
    createAcctBtn.addEventListener('click', async(e) => {
        const firstName = document.getElementById("first-name").value;
        const lastName = document.getElementById("last-name").value;
        const email = document.getElementById("signup-email").value;
        const password = document.getElementById("signup-password").value;

        const confirmPassword = document.getElementById("confirm-pw").value;

        try {
            const response = await fetch("http://127.0.0.1:8080/auth/signup", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    firstName: firstName,
                    lastName: lastName,
                    email: email,
                    password: password,
                    confirmPassword: confirmPassword

                }),
            });
        
            if (response.ok) {
                console.log("Sign up SUCCESSFULL");
                window.location.href = "course.html";
            }
        } catch (error) {
            console.log("LOGIN ERROR:", error)
        }

    })
}
