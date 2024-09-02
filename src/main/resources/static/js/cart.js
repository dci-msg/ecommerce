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

function addToCart(bookId, quantity){
    fetch(`/cart/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `bookId=${bookId}&quantity=${quantity}`
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            }
        })
        .catch(error => console.error('Error:', error));
}

function checkEnter(event) {
    if (event.key === 'Enter') {
        applyCoupon();
    }
}

function applyCoupon() {
    const couponCode = document.getElementById('couponCode').value;
    fetch(`/cart/applyCoupon`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `couponCode=${couponCode}`
    })
        .then(response => response.json())
        .then(data => {
            const couponMsg = document.getElementById('couponMsg');
            if (data.couponIsValid) {
                couponMsg.innerText = 'Coupon applied successfully!';
                couponMsg.classList.add('text-success');
                couponMsg.classList.remove('text-danger');
            } else {
                couponMsg.innerText = 'Invalid coupon code.';
                couponMsg.classList.add('text-danger');
                couponMsg.classList.remove('text-success');
            }
        })
        .catch(error => console.error('Error:', error));
}