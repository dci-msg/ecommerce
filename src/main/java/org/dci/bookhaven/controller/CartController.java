package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Cart;
import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.service.CartService;
import org.dci.bookhaven.service.LineItemService;
import org.dci.bookhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
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

    @GetMapping("")
    public String viewCart(Model model) {
        if(userService.getLoggedInUser() == null) {
            return "redirect:/login";
        }

        boolean isLoggedIn = userService.isLoggedIn();
        model.addAttribute("isLoggedIn", isLoggedIn);

        User user = userService.getLoggedInUser();
        Long userId = user.getId();
        Cart cart = cartService.getOrCreateCart(userId);
        model.addAttribute("cart", cart);

        if(cart.getLineItems() != null && !cart.getLineItems().isEmpty()) {
            model.addAttribute("lineItems", cart.getLineItems());
            Map<Long, BigDecimal> lineTotals = new HashMap<>();
            for(LineItem lineItem : cart.getLineItems()) {
                lineTotals.put(lineItem.getId(), lineItemService.getLineTotal(lineItem));
            }
            model.addAttribute("lineTotals", lineTotals);
        }

        if(cart.getLineItems()!=null && !cart.getLineItems().isEmpty()){
            model.addAttribute("cartSize", cart.getLineItems().size());
        }else{
            model.addAttribute("cartSize", 0);
        }

        BigDecimal total = cartService.getTotal(cart.getId());
        model.addAttribute("total", total);

        if(cart.getLineItems() != null && cart.getLineItems().isEmpty()) {
            model.addAttribute("isEmpty", true);
        } else {
            model.addAttribute("isEmpty", false);
        }


        return "cart";
    }




}
