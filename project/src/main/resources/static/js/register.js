        document.getElementById('registrationForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent default form submission

        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, email, username, password })
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || 'Network response was not ok ' + response.statusText);
                });
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            if (data.success) {
             // Display the response data on success
                document.getElementById('response').innerHTML = `
                    <p>${data.data} is registered successfully </p>
                `;
                document.getElementById('response').className = 'alert alert-success';
        console.log('Data to store in localStorage:', data.data);
        try {
            localStorage.setItem('newUser', data.data);
            console.log('Data stored successfully');
        } catch (e) {
            console.error('Error storing data in localStorage:', e);
        }
                setTimeout(function() {
                    window.location.href = '/';
                }, 2000);
                return;
            } else {
                document.getElementById('response').innerHTML = `
                   <p>false line one</p>
                   <p>Error: ${data.data}</p>
               `;
               document.getElementById('response').className = 'alert alert-danger';
            }
              document.getElementById('response').style.display = 'block';
        })
        .catch(error => {
               console.error('There was a problem with the fetch operation:', error);
                document.getElementById('response').innerHTML = `
                    <p>Status: Error</p>
                    <p> this is error line </p>
                    <p>Error: ${error.message}</p>
                `;
                document.getElementById('response').className = 'alert alert-danger';
                document.getElementById('response').style.display = 'block';
        });
           setTimeout(function() {
                location.reload(); // Refresh the page after 3 seconds
             }, 3000);
    });