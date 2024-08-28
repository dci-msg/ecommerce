package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Cart;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.service.CartService;
import org.dci.bookhaven.service.LineItemService;
import org.dci.bookhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping()
public class CartController {

    private final CartService cartService;
    private final LineItemService lineItemService;
    private final UserService userService;

    @Autowired
    public CartController(
            CartService cartService,
            LineItemService lineItemService,
            UserService userService) {
        this.cartService = cartService;
        this.lineItemService = lineItemService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        User user = userService.getLoggedInUser();
        if (user != null) {
            Long userId = user.getId();
            Cart cart = cartService.getOrCreateCart(userId);
            model.addAttribute("cart", cart);
            model.addAttribute("lineItems", cart.getLineItems());
            model.addAttribute("cartTotal", cartService.getCartTotal(cart.getId()));
            model.addAttribute("isEmpty", cart.getLineItems().isEmpty());
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "cart";
    }

    @PostMapping("/update-quantity")
    @ResponseBody
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
    @ResponseBody
    public Map<String, Double> applyCoupon(
            @RequestParam("cartId") Long cartId,
            @RequestParam("couponCode") String couponCode
    ) {
        double cartTotalAfterCoupon = cartService.getCartTotalAfterCoupon(cartId, couponCode);
        Map<String, Double> response = new HashMap<>();
        response.put("cartTotalAfterCoupon", cartTotalAfterCoupon);
        return response;
    }

    @PostMapping("/add-to-cart")
    public String addToCart(
            @RequestParam("bookId") Long bookId,
            @RequestParam("quantity") int quantity
    ) {
        User user = userService.getLoggedInUser();
        Long userId = user.getId();
        if (user != null) {
            Cart cart = cartService.getOrCreateCart(userId);
            cartService.addToCart(cart.getId(), bookId, quantity);
            return "redirect:/cart/";
        } else {
            // Handle the case where userId is null (e.g., user not authenticated)
            return "redirect:/login";
        }
    }


}
