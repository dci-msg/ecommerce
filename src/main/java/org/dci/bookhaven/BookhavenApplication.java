package org.dci.bookhaven;

import org.dci.bookhaven.model.*;
import org.dci.bookhaven.service.*;
import org.dci.bookhaven.util.UtilGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookhavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookhavenApplication.class, args);
    }

    UserService userService;
    UserTypeService userTypeService;
    CartService cartService;
    LineItemService lineItemService;
    BookService bookService;
    UtilGenerator generator;


    @Autowired
    public BookhavenApplication(
            UserService userService,
            UserTypeService userTypeService,
            CartService cartService,
            LineItemService lineItemService,
            BookService bookService,
            UtilGenerator generator
            ) {
        this.userService = userService;
        this.userTypeService = userTypeService;
        this.cartService = cartService;
        this.lineItemService = lineItemService;
        this.bookService = bookService;
        this.generator = generator;
    }
//
//
//    // Create 20 users
//    @Bean
//    CommandLineRunner runner1() {
//        return args -> {
//
//            for(int i = 0; i < 20; i++) {
//                User user = new User();
//                user.setEmail("user" + i + "@example.com");
//                user.setPassword("password" + i);
//                userService.registerNewUserAccount(user);
//            }
//        };
//    }
//
//    // Create 50 books
//    @Bean
//    CommandLineRunner runner2() {
//        return args -> {
//
//            for(int i = 0; i < 50; i++) {
//                Book book = new Book();
//                book.setTitle("Book " + i);
//                book.setAuthor("Author " + i);
//                book.setPrice(generator.generateRandomPrice());
//                book.setIsbn("ISBN" + i);
//                book.setPublicationDate(generator.generateRandomLocalDate());
//                book.setPages(100 + i);
//                book.setLanguage("English");
//                book.setDescription("Description " + i);
//                book.setImagePath("book_01.jpg");
//                bookService.addBook(book);
//            }
//        };
//    }


    // Create 8 addresses
    // Create 6 orders



}
