package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Order;
import org.dci.bookhaven.model.Shipping;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.service.OrderService;
import org.dci.bookhaven.service.UserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserProfileService userProfileService;

    public OrderController(OrderService orderService, UserProfileService userProfileService) {
        this.orderService = orderService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("")
    public String viewAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/order/{id}")
    public String viewOrderDetails(Model model, @PathVariable("id") long id) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);

        Shipping shipping = order.getShipping();
        model.addAttribute("shipping", shipping);

        User user = order.getUser();
        model.addAttribute("user", user);

        UserProfile userProfile = userProfileService.getUserProfileByUserId(user.getId());
        model.addAttribute("userProfile", userProfile);

        String billingAddress = order.getBillingAddress().toReader();
        model.addAttribute("billingAddress", billingAddress);

        String shippingAddress = shipping.getAddress().toReader();
        model.addAttribute("shippingAddress", shippingAddress);

        return "order/order-detail";
    }

    @GetMapping("/order/{id}/close")
    public String closeOrder(Model model, @PathVariable("id") long id) {
        orderService.close(id);
        return "redirect:/orders";
    }

    @GetMapping("/order/{id}/reopen")
    public String reopenOrder(Model model, @PathVariable("id") long id) {
        orderService.reopen(id);
        return "redirect:/orders";
    }


}
