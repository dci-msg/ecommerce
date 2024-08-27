// Set your publishable key: remember to change this to your live publishable key in production
// See your keys here: https://dashboard.stripe.com/apikeys
const stripe = Stripe('pk_test_51PniUQL6UNiuiJrzosPKddSp966OvcmRbO3bNcMUPY3YaSHywJHm8CxGC2OUzGBTCBBUhgmLbx0UkS0a26dafmIQ001h7hxKNR');

const options = {
    clientScret: '{{CLIENT_SECRET}}',
    appearance: {
        theme: 'flat',
        locale: 'de'
    }
};

// Set up Stripe.js and Elements to use in checkout form, passing the client secret obtained in a previous step
const elements = stripe.elements(options);

// Create and mount the Payment Element
const paymentElement = elements.create('payment');
paymentElement.mount('#payment-element');

// Submit the payment To Stripe
const form = document.getElementById('payment-form');

form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const {error} = await stripe.confirmPayment({
        //`Elements` instance that was used to create the Payment Element
        elements,
        confirmParams: {
            return_url: 'localhost:8080/checkout/success',
        },
    });

    if (error) {
        // This point will only be reached if there is an immediate error when
        // confirming the payment. Show error to your customer (for example, payment
        // details incomplete)
        const messageContainer = document.querySelector('#error-message');
        messageContainer.textContent = error.message;
    } else {
        // Your customer will be redirected to your `return_url`. For some payment
        // methods like iDEAL, your customer will be redirected to an intermediate
        // site first to authorize the payment, then redirected to the `return_url`.
    }
});

// Retrieve the PaymentIntent.
// Initialize Stripe.js using your publishable key
const stripe = Stripe('pk_test_51PniUQL6UNiuiJrzosPKddSp966OvcmRbO3bNcMUPY3YaSHywJHm8CxGC2OUzGBTCBBUhgmLbx0UkS0a26dafmIQ001h7hxKNR');

// Retrieve the "payment_intent_client_secret" query parameter appended to
// your return_url by Stripe.js
const clientSecret = new URLSearchParams(window.location.search).get(
    'payment_intent_client_secret'
);

// Retrieve the PaymentIntent
stripe.retrievePaymentIntent(clientSecret).then(({paymentIntent}) => {
    const message = document.querySelector('#message')

    // Inspect the PaymentIntent `status` to indicate the status of the payment
    // to your customer.
    //
    // Some payment methods will [immediately succeed or fail][0] upon
    // confirmation, while others will first enter a `processing` state.
    //
    // [0]: https://stripe.com/docs/payments/payment-methods#payment-notification
    switch (paymentIntent.status) {
        case 'succeeded':
            message.innerText = 'Success! Payment received.';
            break;

        case 'processing':
            message.innerText = "Payment processing. We'll update you when payment is received.";
            break;

        case 'requires_payment_method':
            message.innerText = 'Payment failed. Please try another payment method.';
            // Redirect your user back to your payment page to attempt collecting
            // payment again
            break;

        default:
            message.innerText = 'Something went wrong.';
            break;
    }
});
