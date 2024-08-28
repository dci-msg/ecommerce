package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Cart;
import org.springframework.data.jpa.repository.Modifying;

public interface CartService {
    // Add x number of books to shopping cart
    @Modifying
    @Transactional
    void addToCart(Long cartId, Long bookId, int quantity);

    // Remove book from shopping cart
    void remove(Long cartId, Long bookId);


    @Modifying
    @Transactional
    void clearCartAndDelete(Long cartId);

    // Get shopping cart by id
    Cart getCartById(Long cartId);

    int getCartSize(Long cartId);

    // Get shopping cart total
    double getCartTotal(Long cartId);

    double getCartTotalAfterCoupon(Long cartId, String couponCode);

    // Retrieve the latest cart and close all other carts
    @Modifying
    @Transactional
    Cart getRecentCartAndCloseOtherCarts(Long userId);
}
