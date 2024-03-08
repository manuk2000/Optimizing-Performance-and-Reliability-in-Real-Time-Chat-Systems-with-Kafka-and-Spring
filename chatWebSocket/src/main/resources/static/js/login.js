let userEmail = null
document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    userEmail = document.getElementById('userEmail').value;
    let password = document.getElementById('password').value;

    // You can perform validation here if needed

    // Example: Checking if both fields are filled
    if (!userEmail || !password) {
        displayErrorMessage('Please enter both user email and password.');
        return;
    }

    // Send login request to the server (you need to implement this)
    // For simplicity, we are assuming the server endpoint is /login
    fetch('http://localhost:8000/api/v1/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: userEmail,
            password: password
        })
    })
        .then(response => {
            if (response.ok) {
                saveToLocalStorage("userEmail" ,userEmail )
                // Redirect to the chat page on successful login
                window.location.href = 'chat.html';
            } else {
                // Handle failed login
                displayErrorMessage('Invalid username or password.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            displayErrorMessage('An error occurred. Please try again later.');
        });

});


// Putting data into localStorage
function saveToLocalStorage(key, data) {
    localStorage.setItem(key, JSON.stringify(data));
}
function displayErrorMessage(message) {
    document.getElementById('errorMessage').textContent = message;
}
