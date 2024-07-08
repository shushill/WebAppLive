document.getElementById('fetchButton').addEventListener('click', function() {
    fetch('/hello', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json(); // Parse the JSON from the response
    })
      .then(data => {
            console.log(data);
                   if (data.success) {
                    // Display the response data on success
                    document.getElementById('response').innerHTML = `
                        <p>Status: ${data.status}</p>
                        <p>Success: ${data.success}</p>
                        <p>Data: ${data.data}</p>
                    `;
                } else {
                    // Handle specific error scenarios
                    if (data.status === "error") {
                        document.getElementById('response').innerHTML = `
                            <p>Status: ${data.status}</p>
                            <p>Error: ${data.data}</p>
                        `;
                    } else {
                        // Handle unexpected response format
                        throw new Error('Unexpected response format');
                    }
                     setTimeout(function() {
                        location.reload();
                    }, 3000);
                }
            })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
});