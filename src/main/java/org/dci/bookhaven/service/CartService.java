package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Cart;
import org.dci.bookhaven.model.User;
import org.springframework.data.jpa.repository.Modifying;

import java.math.BigDecimal;

public interface CartService {
    // Add x number of books to shopping cart
    @Modifying
    @Transactional
    void addToCart(Long cartId, Long bookId, int quantity);

    int getCartSize(Long cartId);

    @Modifying
    Cart getOrCreateCart(Long userId);

    BigDecimal getTotal(Long cartId);

    Cart getCartById(Long cartId);

    Cart getCartByLineItemId(Long lineItemId);

    @Modifying
    @Transactional
    void deleteLineItemById(Long lineItemId);
}
