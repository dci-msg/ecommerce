package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Category;
import org.dci.bookhaven.service.BookService;
import org.dci.bookhaven.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    private final BookService bookService;

    private final CategoryService categoryService;

    public CustomerController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard")
    public String showCustomerPage(Model model){
        List<Book> books = bookService.getBooks();
        List<Category> categories = categoryService.getCategoriesAsc();

        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        System.out.println("books    " + books.size());
        return "dashboard";
    }
}