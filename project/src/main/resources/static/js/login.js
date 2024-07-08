document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/login_req', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // Parse the JSON response
        } else {
            return response.json().then(errorData => {
                throw new Error(errorData.data || 'Network response was not ok ' + response.statusText);
            });
        }
    })
    .then(data => {
        console.log(data);
        if (data.success) {
            // Display success message
            document.getElementById('response').innerHTML = `
                <p>Login successful</p>
            `;


            document.getElementById('response').className = 'alert alert-success';

             window.location.href = data.redirectUrl;
        } else {
            // Display error message
            document.getElementById('response').innerHTML = `
                <p>Login failed</p>
                <p>${data.data}</p>
            `;
            document.getElementById('response').className = 'alert alert-danger';
        }
        document.getElementById('response').style.display = 'block';
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
        document.getElementById('response').innerHTML = `
            <p>Status: Error</p>
            <p>${error.message}</p>
        `;
        document.getElementById('response').className = 'alert alert-danger';
        document.getElementById('response').style.display = 'block';
    })
    .finally(() => {
        setTimeout(function() {
            location.reload();
        }, 3000);
    });
});

window.onload = function() {
    if (localStorage.getItem('newUser') !== null) {
        const data = localStorage.getItem('newUser');

        const userDataElement = document.getElementById('newUserData');
        const paragraph = document.createElement('p');
        paragraph.textContent = `${data} Please log in here!! `;
        userDataElement.appendChild(paragraph);

        localStorage.removeItem('newUser'); // Corrected method name to removeItem
    }
}
