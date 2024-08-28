package org.dci.bookhaven.service.impl;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Cart;
import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.repository.CartRepository;
import org.dci.bookhaven.service.BookService;
import org.dci.bookhaven.service.CartService;
import org.dci.bookhaven.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private BookService bookService;
    private LineItemService lineItemService;

    @Autowired
    public CartServiceImpl(
            CartRepository cartRepository,
            BookService bookService,
            LineItemService lineItemService) {
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.lineItemService = lineItemService;
    }

    // Add book to shopping cart
    @Modifying
    @Transactional
    @Override
    public void addToCart(Long cartId, Long bookId) {
        // Retrieve shopping cart from database
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Retrieve book from database
        Book book = bookService.getBookById(bookId);

        // Check if book is already in shopping cart
        List<LineItem> lineItems = cart.getLineItems();
        for (LineItem lineItem : lineItems) {
            // If book is already in shopping cart, increase quantity by 1
            if (lineItem.getBook().getId() == bookId) {
                int newQuantity = lineItem.getQuantity() + 1;
                lineItemService.updateLineItemQuantity(lineItem.getId(), newQuantity);

                // Update shopping cart
                cart.setLineItems(lineItems);
                cartRepository.save(cart);

                return;
            }
        }

        // If book is not in shopping cart, create new line item
        LineItem lineItem = LineItem.builder()
                .book(book)
                .quantity(1)
                .build();
        lineItemService.addLineItem(lineItem);

        // Add line item to shopping cart
        lineItems.add(lineItem);

        // Update shopping cart
        cart.setLineItems(lineItems);
        cartRepository.save(cart);

    }

    // Remove book from shopping cart
    @Modifying
    @Transactional
    @Override
    public void remove(Long cartId, Long bookId) {
        // Retrieve shopping cart from database
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Retrieve line items from shopping cart
        List<LineItem> lineItems = cart.getLineItems();

        // Find line item with book id
        for (LineItem lineItem : lineItems) {
            if (lineItem.getBook().getId() == bookId) {
                // Remove line item from shopping cart
                lineItems.remove(lineItem);
                lineItemService.deleteLineItem(lineItem.getId());

                // Update shopping cart
                cart.setLineItems(lineItems);
                cartRepository.save(cart);

                return;
            }
        }

        throw new RuntimeException("Book not found in cart");
    }

    // Clear shopping cart

    @Modifying
    @Transactional
    @Override
    public void clearCart(Long cartId) {
        // Retrieve shopping cart from database
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Retrieve line items from shopping cart
        List<LineItem> lineItems = cart.getLineItems();

        // Remove all line items from shopping cart
        for (LineItem lineItem : lineItems) {
            lineItemService.deleteLineItem(lineItem.getId());
        }

        // Update shopping cart
        cart.setLineItems(null);
        cartRepository.save(cart);
    }
}
