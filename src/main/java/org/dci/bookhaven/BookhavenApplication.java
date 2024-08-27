package org.dci.bookhaven;

import com.stripe.model.PaymentIntent;
import org.dci.bookhaven.model.*;
import org.dci.bookhaven.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class BookhavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookhavenApplication.class, args);
    }

//    UserService userService;
//    UserTypeService userTypeService;
//    OrderService orderService;
//
//    @Autowired
//    public BookhavenApplication(UserService userService, UserTypeService userTypeService, OrderService orderService) {
//        this.userService = userService;
//        this.userTypeService = userTypeService;
//        this.orderService = orderService;
//    }

////    // Create 2 roles
////    @Bean
////    CommandLineRunner runner0(){
////        return args -> {
////            UserType admin = new UserType(1L, "ADMIN");
////            UserType user = new UserType(2L, "USER");
////            userTypeService.createUserType(admin);
////            userTypeService.createUserType(user);
////        };
////    }
//
//    // Create 5 users
//    @Bean
//    CommandLineRunner runner1() {
//        return args -> {
//            for(int i = 0; i < 5; i++) {
//                UserType customer = userTypeService.getUserTypeByName("CUSTOMER");
//                User user = new User();
//                user.setEmail("user" + i + "@example.com");
//                user.setPassword("password" + i);
//                user.setActive(true);
//                user.setUserType(customer);
//                userService.registerNewUserAccount(user);
//            }
//        };
//    }
//
////    // Create 10 books
////    CommandLineRunner runner2() {
////        return args -> {
////        };
////    }
//
//
//    // Create 8 addresses
//    @Bean
//    CommandLineRunner runner3() {
//        return args -> {
//            for(int i = 0; i < 8; i++) {
//                Address address = new Address();
//                address.setStreet("Street " + i);
//                address.setCity("City " + i);
//                address.setState("State " + i);
//                address.setZip("Zip " + i);
//                address.setCountry("Country " + i);
//
//                addressService.createAddress(address);
//            }
//        };
//    }

//    // Create 6 orders
//    @Bean
//    CommandLineRunner runner4() {
//        return args -> {
//            List<User> users = userService.getAllUsers();
//            List<Address> addresses = addressService.findAllAddresses();
//
//            // Get Random total price
//            Random random = new Random();
//
//            for(int i=0; i<6; i++) {
//                Order order = new Order();
//
//                order.setUser(users.get(i));
//                order.setCurrency("eur");
//                order.setBillingAddress(addresses.get(i));
//                order.setTotal(random.nextDouble(0, 100));
//                order.setStatus("OPEN");
//                orderService.createOrder(order);
//            }
//
//        };
//    }



}
