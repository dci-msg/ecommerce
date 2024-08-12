package org.muyangj.bookhaven.controllers;

import org.muyangj.bookhaven.models.Book;
import org.muyangj.bookhaven.models.Category;
import org.muyangj.bookhaven.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBooks(Model model) {
        List<Book> books = bookService.getBooks();

       // model.addAttribute("books", books);
        return "index";
    }

    // Add books It can do only admin - GET request
    @GetMapping("/add")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    @PostMapping("/add")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBook(Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    // Edit books by id (all filed together) and only admin can do- GET request
    @GetMapping("/edit/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);

        model.addAttribute("book", book);
        return "books/form";
    }

    @PostMapping("/edit/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBook(@PathVariable Long id,
                           @RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam("price") BigDecimal price,
                           @RequestParam("isbn") String isbn,
                           @RequestParam("publication-date") LocalDate publicationDate,
                           @RequestParam("pages") Integer pages,
                           @RequestParam("language") String language,
                           @RequestParam("description") String description,
                           @RequestParam("category") Category category,
                              Model model
    ) {
        bookService.updateBook(new Book(id, author, title, price, isbn,
                publicationDate, pages, language, description, category));
        return "redirect:/books";
    }

    // Delete Book by id  and can do that only admin-Get request
    @GetMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}