package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.*;
import org.dci.bookhaven.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BookController {

    private final BookService bookService;

    private final CategoryService categoryService;

    private final UserService userService;

    private final CartService cartService;

    private final LikedBookService likedBookService;

    public BookController(BookService bookService, CategoryService categoryService,
                          LikedBookService likedBookService, UserService userService, CartService cartService) {
        this.likedBookService = likedBookService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/manage-books")
    public String books(Model model) {
        List<Book> books = bookService.getBooks();

        model.addAttribute("books", books);
        return "book/manage-books";
    }

    @GetMapping("/searching")
    public String searching(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        return "book/manage-books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String keyword,
                              @RequestParam(value = "categoryId", required = false) Long categoryId,
                              @RequestParam String priceCriteria,
                              @RequestParam String language,
                              Model model)
    {
        List<Book> books = bookService.getBooks(keyword, categoryId, priceCriteria, language);
        List<Category> categories = categoryService.getCategoriesAsc();

        model.addAttribute("categories", categories);
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/")
    public String bookhaven(Model model) {
        List<Book> books = bookService.getBooks();
        List<Category> categories = categoryService.getCategoriesAsc();
        Set<Book> likedBooks = new HashSet<>();

        User user = userService.getLoggedInUser();
        if (user != null) {
            likedBooks = likedBookService.likedBooks(user.getId());
            Cart cart = cartService.getOrCreateCart(user.getId());
            int cartSize = cartService.getCartItemNumber(cart);
            model.addAttribute("cartSize", cartSize);
        }
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("likedBooks", likedBooks);
        System.out.println("books    " + books.size());
        return "index";
    }

    // Add books It can do only admin - GET request
    @GetMapping("/add-book")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getCategoriesAsc());
        return "book/add-book";
    }

    @PostMapping("/add-book")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBook(@ModelAttribute Book book) {
        System.out.println(book);
        bookService.addBook(book);
        System.out.println(book);
        return  "redirect:/manage-books";
    }

    // Edit books by id (all filed together) and only admin can do- GET request
    @GetMapping("/edit-book/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);

        model.addAttribute("categories", categoryService.getCategoriesAsc());
        model.addAttribute("book", book);
        return "book/edit-book";
    }

    @PostMapping("/edit-book/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBook(@PathVariable Long id,
                           @RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam("price") BigDecimal price,
                           @RequestParam("isbn") String isbn,
                           @RequestParam("publicationDate") LocalDate publicationDate,
                           @RequestParam("pages") Integer pages,
                           @RequestParam("language") String language,
                           @RequestParam("description") String description,
                           @RequestParam("category") Category category,
                           @RequestParam("imagePath") String imagePath
    ) {
        Book book = new  Book(id, author, title, price, isbn,
                publicationDate, pages, language, description, imagePath, category);

        bookService.updateBook(book);

        return "redirect:/manage-books";
    }

    // Delete Book by id  and can do that only admin-Get request
    @GetMapping("/delete-book/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/manage-books";
    }

    @GetMapping("/book-details/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String bookDetails(@PathVariable Long id, Model model) {
        System.out.println("I am here!!!!");
        Book book = bookService.getBookById(id);

        model.addAttribute("categories", categoryService.getCategoriesAsc());
        model.addAttribute("book", book);
        return "subpage";
    }

    @GetMapping("/book/{bookId}")
    public String viewBookDetail(@PathVariable Long bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        if(userService.isLoggedIn()){
            boolean isLoggedIn = true;
            model.addAttribute("isLoggedIn", isLoggedIn);
            Cart cart = cartService.getOrCreateCart(userService.getLoggedInUser().getId());
            int cartSize = cartService.getCartItemNumber(cart);
            model.addAttribute("cartSize", cartSize);
        } else{
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("cartSize", 0);
        }
        return "book-detail";
    }

}
