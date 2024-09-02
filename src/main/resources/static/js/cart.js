function increaseQuantity(lineItemId) {
    fetch(`/cart/increaseQuantity`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `lineItemId=${lineItemId}`
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            }
        })
        .catch(error => console.error('Error:', error));
}


function decreaseQuantity(lineItemId) {
    fetch(`/cart/decreaseQuantity`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `lineItemId=${lineItemId}`
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            }
        })
        .catch(error => console.error('Error:', error));
}