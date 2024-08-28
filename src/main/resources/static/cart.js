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