package org.dci.bookhaven.service.impl;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.*;
import org.dci.bookhaven.repository.CartRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;
    private UserService userService;
    private CartRepository cartRepository;
    private BookService bookService;
    private LineItemService lineItemService;
    private CouponService couponService;

    @Autowired
    public CartServiceImpl(
            UserRepository userRepository,
            UserService userService,
            CartRepository cartRepository,
            BookService bookService,
            LineItemService lineItemService,
            CouponService couponService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.lineItemService = lineItemService;
        this.couponService = couponService;
    }

    // Add x number of books to shopping cart
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
                .quantity(1)
                .build();
        lineItemService.addLineItem(lineItem);

        // Add line item to shopping cart
        lineItems.add(lineItem);

        // Update shopping cart
        cart.setLineItems(lineItems);
        sortCartByBookTitle(cart);
        cartRepository.save(cart);
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


    @Override
    public BigDecimal getTotalAfterCouponAndShipping(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        BigDecimal total = getTotal(cartId);

        // Apply coupon discount percentage
        if(cart.getCoupon() != null && !cart.getCoupon().isEmpty() && couponService.isValid(cart.getCoupon())){
            Coupon coupon = couponService.getByCode(cart.getCoupon());
            BigDecimal discount = coupon.getDiscount().divide(new BigDecimal(100));
            total = total.subtract(total.multiply(discount));
        }

        // Apply shipping cost
        if(cart.getShippingMethod() != null){
            if(cart.getShippingMethod().equals("standard")){
                total = total.add(new BigDecimal(5));
            } else if(cart.getShippingMethod().equals("express")){
                total = total.add(new BigDecimal(10));
            }
        }

        return total;
    }

    @Override
    public Cart getCartByLineItemId(Long lineItemId){
        List<Cart> carts = cartRepository.findAll();
        for(Cart cart : carts){
            List<LineItem> lineItems = cart.getLineItems();
            for(LineItem lineItem : lineItems){
                if(lineItem.getId() == lineItemId){
                    return cart;
                }
            }
        }
        throw new RuntimeException("Cart not found");
    }

    @Modifying
    @Transactional
    @Override
    public void deleteLineItemById(Long lineItemId){
        Cart cart = getCartByLineItemId(lineItemId);
        List<LineItem> lineItems = cart.getLineItems();
        lineItems.removeIf(lineItem -> lineItem.getId() == lineItemId);
        cart.setLineItems(lineItems);
        sortCartByBookTitle(cart);
        cartRepository.save(cart);
        lineItemService.deleteLineItemById(lineItemId);
    }

    @Modifying
    @Transactional
    @Override
    public void applyCoupon(Long cartId, String couponCode){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if(couponService.isValid(couponCode)){
            cart.setCoupon(couponCode);
            cartRepository.save(cart);
        }
    }


    @Modifying
    @Transactional
    @Override
    public void sortCartByBookTitle(Cart cart){
        List<LineItem> lineItems = cart.getLineItems();
        lineItems.sort((l1, l2) -> l1.getBook().getTitle().compareTo(l2.getBook().getTitle()));
        cart.setLineItems(lineItems);
        cartRepository.save(cart);
    }

    @Modifying
    @Transactional
    @Override
    public void updateShipping(Long cartId, String shippingMethod) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setShippingMethod(shippingMethod);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getShippingCost(String shippingMethod){
        if(shippingMethod.equals("standard")){
            return new BigDecimal(5);
        } else if(shippingMethod.equals("express")){
            return new BigDecimal(10);
        }
        return new BigDecimal(0);
    }

    @Override
    public BigDecimal getCouponDiscountedValue(Long cartId, String couponCode){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if (couponService.isValid(couponCode)) {
            Coupon coupon = couponService.getByCode(couponCode);
            BigDecimal discount = coupon.getDiscount().divide(new BigDecimal(100));
            return getTotal(cartId).multiply(discount);
        }
        return new BigDecimal(0);
    }

    @Override
    public int getCartItemNumber(Cart cart) {
        int size = 0;
        if(cart.getLineItems()!=null && !cart.getLineItems().isEmpty()){
            List<LineItem> lineItems = cart.getLineItems();
            for(LineItem lineItem : lineItems){
                size += lineItem.getQuantity();
            }
        }
        return size;
    }

}
