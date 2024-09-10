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

function addToCart(bookId){
    fetch(`/cart/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `bookId=${bookId}`
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            }
        })
        .catch(error => console.error('Error:', error));
}


document.addEventListener('DOMContentLoaded', (event) => {
    const couponCodeInput = document.getElementById('couponCode');
    couponCodeInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            applyCoupon();
        }
    });

    const shippingSelect = document.getElementById('shippingMethod');
    if (shippingSelect) {
        // Set the dropdown value based on the stored shipping method
        const storedShippingMethod = shippingSelect.getAttribute('data-stored-shipping-method');
        if (storedShippingMethod) {
            shippingSelect.value = storedShippingMethod;
            toggleAddressCards(storedShippingMethod);
        }

        shippingSelect.addEventListener('change', function(event) {
            updateShippingCost(event.target.value);
            toggleAddressCards(event.target.value);
        });
    }

});

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
            if (data.couponIsValid) {
                window.location.reload(); // Refresh the page to apply the coupon
            } else {
                const couponMsg = document.getElementById('couponMsg');
                couponMsg.innerText = 'Invalid coupon code.';
                couponMsg.classList.add('text-danger');
                couponMsg.classList.remove('text-success');
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateShippingCost(shippingMethod) {
    let shippingMethodString;
    switch (shippingMethod) {
        case 'pickup':
            shippingMethodString = 'pickup';
            break;
        case 'standard':
            shippingMethodString = 'standard';
            break;
        case 'express':
            shippingMethodString = 'express';
            break;
        default:
            shippingMethodString = '';
    }

    fetch(`/cart/chooseShipping`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `shippingMethod=${shippingMethodString}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'redirect:/login') {
                window.location.href = '/login';
            } else {
                window.location.reload(); // Refresh the page to apply the shipping method
            }
        })
        .catch(error => console.error('Error:', error));
}

function toggleAddressCards(shippingMethod) {
    const addressCard = document.getElementById('addressCard');
    const storeAddressCard = document.getElementById('storeAddressCard');

    if (shippingMethod === 'pickup') {
        addressCard.style.display = 'none';
        storeAddressCard.style.display = 'block';
    } else if (shippingMethod === 'standard' || shippingMethod === 'express') {
        addressCard.style.display = 'block';
        storeAddressCard.style.display = 'none';
    } else {
        addressCard.style.display = 'none';
        storeAddressCard.style.display = 'none';
    }
}