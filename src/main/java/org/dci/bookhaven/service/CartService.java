package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface CartService {
    // Add book to shopping cart
    void addToCart(Long cartId, Long bookId);

    // Remove book from shopping cart
    void remove(Long cartId, Long bookId);

    @Modifying
    @Transactional
    void clearCart(Long cartId);
}
