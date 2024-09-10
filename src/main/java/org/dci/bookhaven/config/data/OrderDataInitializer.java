package org.dci.bookhaven.config.data;

import org.dci.bookhaven.model.*;
import org.dci.bookhaven.service.*;
import org.dci.bookhaven.util.UtilGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
@org.springframework.core.annotation.Order(DataInitOrder.ORDER)
public class OrderDataInitializer {

    private final OrderService orderService;
    private final LineItemService lineItemService;
    private final BookService bookService;
    private final UserService userService;
    private final AddressService addressService;
    private final ShippingService shippingService;
    private final UtilGenerator utilGenerator;

    public OrderDataInitializer(
            OrderService orderService,
            LineItemService lineItemService,
            BookService bookService,
            UserService userService,
            AddressService addressService,
            ShippingService shippingService,
            UtilGenerator utilGenerator) {
        this.orderService = orderService;
        this.lineItemService = lineItemService;
        this.bookService = bookService;
        this.userService = userService;
        this.addressService = addressService;
        this.shippingService = shippingService;
        this.utilGenerator = utilGenerator;
    }


    public void run(String... args) throws Exception {
        Random random = new Random();

        // Create lineItems
        List<Book> books = bookService.getBooks();
        for (int i = 0; i < 20; i++) {
            int randomBookIndex = random.nextInt(books.size());
            Book randomBook = books.get(randomBookIndex);
            LineItem lineItem = new LineItem();
            lineItem.setQuantity(random.nextInt(3) + 1);
            lineItem.setBook(randomBook);
            lineItemService.create(lineItem);
        }

        List<LineItem> lineItems = lineItemService.getLineItems();

        // Create shipping
        List<Address> addresses = addressService.getAddresses();
        List<String> shippingMethods = List.of("Standard", "Express", "Pickup");
        for (int i = 0; i < 10; i++) {
            int randomAddressIndex = random.nextInt(addresses.size());
            Address randomAddress = addresses.get(randomAddressIndex);
            Shipping shipping = new Shipping();
            shipping.setAddress(randomAddress);

            int randomShippingIndex = random.nextInt(3);
            switch (randomShippingIndex) {
                case 0:
                    shipping.setShippingMethod("Standard");
                    shipping.setCost(BigDecimal.valueOf(5));
                    break;
                case 1:
                    shipping.setShippingMethod("Express");
                    shipping.setCost(BigDecimal.valueOf(10));
                    break;
                case 2:
                    shipping.setShippingMethod("Pickup");
                    shipping.setCost(BigDecimal.valueOf(0));
                    break;
            }

            String trackingNumber = String.valueOf(random.nextInt(1000000, 9999999));
            shipping.setTrackingNumber(trackingNumber);

            shippingService.create(shipping);
        }

        List<Shipping> shippings = shippingService.getAllShippings();

        List<User> users = userService.getAllUsers();

        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setUser(users.get(random.nextInt(users.size())));
            // Get sub list of lineItems
            List<LineItem> orderLineItems = lineItems.subList(0, 2);
            lineItems = lineItems.subList(2, lineItems.size());
            order.setLineItems(orderLineItems);

            order.setCouponCode(null);

            Shipping shipping = shippings.get(random.nextInt(shippings.size()));
            shipping = shippingService.findById(shipping.getId()); // Ensure the entity is managed
            order.setShipping(shipping);

            BigDecimal total = lineItemService.getLineTotal(orderLineItems.get(0)).add(lineItemService.getLineTotal(orderLineItems.get(1)));
            order.setTotal(total);

            order.setCurrency("EUR");

            order.setBillingAddress(shipping.getAddress());

            order.setStatus("Closed");

            LocalDateTime createdAt = utilGenerator.generateRandomLocalDateTime();
            order.setCreatedAt(createdAt);

            orderService.create(order);
        }
    }
}
