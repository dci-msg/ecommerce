package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Cart;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.service.CartService;
import org.dci.bookhaven.service.LikedBookService;
import org.dci.bookhaven.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LikedBookController {

    private final UserService userService;

    private final LikedBookService likedBookService;

    private final CartService cartService;

    public LikedBookController(UserService userService, LikedBookService likedBookService, CartService cartService) {
        this.userService = userService;
        this.likedBookService = likedBookService;
        this.cartService = cartService;
    }

    @PostMapping("/likes/add")
    public String addToLiked(@RequestParam Long bookId) {

        if(userService.isLoggedIn()) {
            Long userId = userService.getLoggedInUser().getId();
            likedBookService.addLikedBook(userId, bookId);
        } else{
            return "redirect:/login";
        }
        return "redirect:/";
    }

    @GetMapping( "/likes")
    public String likedBook(Model model) {
        Set<Book> likedBooks = new HashSet<>();

        User user = userService.getLoggedInUser();

        if (user != null) {
            likedBooks = likedBookService.likedBooks(user.getId());
            model.addAttribute("likedBooks", likedBooks);

            Cart cart = cartService.getOrCreateCart(user.getId());
            int size = cartService.getCartItemNumber(cart);
            model.addAttribute("cartSize", size);

        } else {
            return "redirect:/login";
        }

        return "liked-books";
    }

    @PostMapping("/likes/delete/{bookId}")
    public String deleteLikedBooked(@PathVariable Long bookId) {
        if(userService.isLoggedIn()) {
            Long userId = userService.getLoggedInUser().getId();
            likedBookService.deleteLikedBook(userId, bookId);
        } else{
            return "redirect:/login";
        }
        return "redirect:/";
    }

    @PostMapping("/likes/delete")
    public String deleteLikeRequest(@RequestParam Long bookId) {
        if(userService.isLoggedIn()) {
            Long userId = userService.getLoggedInUser().getId();
            likedBookService.deleteLikedBook(userId, bookId);
        } else{
            return "redirect:/login";
        }
        return "redirect:/";
    }
}
