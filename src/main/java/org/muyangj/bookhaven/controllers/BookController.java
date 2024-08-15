package org.muyangj.bookhaven.controllers;

import org.muyangj.bookhaven.models.Book;
import org.muyangj.bookhaven.models.Category;
import org.muyangj.bookhaven.services.BookService;
import org.muyangj.bookhaven.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/bookhaven")
public class BookController {

    private final BookService bookService;

    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<Book> books = bookService.getBooks();

        model.addAttribute("books", books);
        return "book/books";
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
        return  "redirect:/bookhaven/books";
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
                           @RequestParam("quantity") Integer quantity,
                           @RequestParam("imagePath") String imagePath
    ) {
        Book book = new  Book(id, author, title, price, isbn,
                publicationDate, pages, language, description,imagePath, quantity,  category);

        bookService.updateBook(book);

        return "redirect:/bookhaven/books";
    }

    // Delete Book by id  and can do that only admin-Get request
    @GetMapping("/delete-book/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/bookhaven/books";
    }
}