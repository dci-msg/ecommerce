package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Cart;
import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.service.CartService;
import org.dci.bookhaven.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final LineItemService lineItemService;

    @Autowired
    public CartController(
            CartService cartService,
            LineItemService lineItemService) {
        this.cartService = cartService;
        this.lineItemService = lineItemService;
    }


    public void addToCart(
            @RequestParam("bookId") Long bookId,
            @RequestParam("cartId") Long shoppingCartId,
            @RequestParam("quantity") int quantity
    ) {
        cartService.addToCart(bookId, shoppingCartId, quantity);
    }

    // Redirect to shopping cart
    @PostMapping("/cart")
    public String shoppingCart(
            Long userId,
            Model model) {
        // Redirect to shopping cart
        Cart cart = cartService.getRecentCartAndCloseOtherCarts(userId);

        model.addAttribute("cart", cart);
        model.addAttribute("lineItems", cart.getLineItems());
        model.addAttribute("total", cartService.getCartTotal(cart.getId()));
        model.addAttribute("totalItems", cart.getLineItems().size());
        model.addAttribute("userId", userId);

        return "cart";
    }

    @PostMapping("/update-quantity")
    public Map<String, Double> updateQuantity(
            @RequestParam("lineItemId") Long lineItemId,
            @RequestParam("quantity") int quantity,
            @RequestParam("cartId") Long cartId
    ) {
        lineItemService.updateQuantity(lineItemId, quantity);
        double lineTotal = lineItemService.getLineTotal(lineItemId);
        double cartTotal = cartService.getCartTotal(cartId);
        Map<String, Double> response = new HashMap<>();
        response.put("lineTotal", lineTotal);
        response.put("cartTotal", cartTotal);
        return response;
    }

    @PostMapping("/apply-coupon")
    public Map<String, Double> applyCoupon(
            @RequestParam("cartId") Long cartId,
            @RequestParam("couponCode") String couponCode
    ) {
        double cartTotalAfterCoupon = cartService.getCartTotalAfterCoupon(cartId, couponCode);
        Map<String, Double> response = new HashMap<>();
        response.put("cartTotalAfterCoupon", cartTotalAfterCoupon);
        return response;
    }
}
