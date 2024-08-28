function updateQuantity(lineItemId, cartId, inputElement) {
    const quantity = inputElement.value;
    fetch('/cart/update-quantity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'lineItemId': lineItemId,
            'quantity': quantity,
            'cartId': cartId
        })
    })
        .then(response => response.json())
        .then(data => {
            const row = inputElement.closest('.row');
            const lineTotalElement = row.querySelector('.line-total');
            const cartTotalElement = document.querySelector('.cart-total');
            lineTotalElement.textContent = data.lineTotal.toFixed(2);
            cartTotalElement.textContent = data.cartTotal.toFixed(2);
        })
        .catch(error => console.error('Error:', error));
}

function applyCoupon(cartId, couponCode) {
    fetch('/cart/apply-coupon', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'cartId': cartId,
            'couponCode': couponCode
        })
    })
        .then(response => response.json())
        .then(data => {
            const cartTotalElement = document.querySelector('.cart-total');
            cartTotalElement.textContent = data.cartTotalAfterCoupon.toFixed(2);
        })
        .catch(error => console.error('Error:', error));
}

function addToCart(bookId, userId, quantity) {
    fetch('/cart/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'bookId': bookId,
            'userId': userId,
            'quantity': quantity
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Item added to cart successfully!');
                document.getElementById('cartSize').innerText = data.cartSize;
            } else {
                alert('Failed to add item to cart.');
            }
        })
        .catch(error => console.error('Error:', error));
}

function goToLogin() {
    alert("fuck");
    window.location.href = '/login';
}

function goToRegister() {
    window.location.href = '/register';
}

function goToCart() {
    window.location.href = '/cart';
}

function goToLogout() {
    window.location.href = '/logout';
}