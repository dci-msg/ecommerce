package org.dci.bookhaven.service.impl;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.*;
import org.dci.bookhaven.repository.CartRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.service.BookService;
import org.dci.bookhaven.service.CartService;
import org.dci.bookhaven.service.CouponService;
import org.dci.bookhaven.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private BookService bookService;
    private LineItemService lineItemService;
    private CouponService couponService;

    @Autowired
    public CartServiceImpl(
            UserRepository userRepository,
            CartRepository cartRepository,
            BookService bookService,
            LineItemService lineItemService,
            CouponService couponService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.lineItemService = lineItemService;
        this.couponService = couponService;
    }

    // Add x number of books to shopping cart
    @Modifying
    @Transactional
    @Override
    public void addToCart(Long cartId, Long bookId, int quantity) {
        // Retrieve shopping cart from database
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Retrieve book from database
        Book book = bookService.getBookById(bookId);

        // Check if book is already in shopping cart
        List<LineItem> lineItems = cart.getLineItems();
        for (LineItem lineItem : lineItems) {
            // If book is already in shopping cart, increase quantity by 1
            if (lineItem.getBook().getId() == bookId) {
                int newQuantity = lineItem.getQuantity() + quantity;
                lineItemService.updateQuantity(lineItem.getId(), newQuantity);

                // Update shopping cart
                cart.setLineItems(lineItems);
                cartRepository.save(cart);

                return;
            }
        }

        // If book is not in shopping cart, create new line item
        LineItem lineItem = LineItem.builder()
                .book(book)
                .quantity(quantity)
                .build();
        lineItemService.addLineItem(lineItem);

        // Add line item to shopping cart
        lineItems.add(lineItem);

        // Update shopping cart
        cart.setLineItems(lineItems);
        cartRepository.save(cart);
    }


    // Get shopping cart size
    @Override
    public int getCartSize(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")).getLineItems().size();
    }


    @Modifying
    @Transactional
    @Override
    public Cart getOrCreateCart(Long userId) {
        List<Cart> carts = cartRepository.findAllByUser_IdAndAndIsOpenTrue(userId);
        if (carts.size() > 0) {
            // Sort cart by created date
            carts.sort((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()));

            Cart latestCart = carts.get(carts.size() - 1);

            // Close all other carts
            for (Cart cart : carts) {
                if (cart.getId() != latestCart.getId()) {
                    cart.setOpen(false);
                    cartRepository.save(cart);
                }
            }
            return latestCart;

        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Cart cart = Cart.builder()
                    .user(user)
                    .isOpen(true)
                    .build();
            cartRepository.save(cart);
            return cart;
        }
    }

    @Override
    public BigDecimal getTotal(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        List<LineItem> lineItems = cart.getLineItems();
        BigDecimal total = new BigDecimal(0);
        for(LineItem lineItem : lineItems){
            total = total.add(lineItemService.getLineTotal(lineItem));
        }
        return total;
    }

}
