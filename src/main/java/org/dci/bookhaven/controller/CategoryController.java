package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Category;
import org.dci.bookhaven.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Show list of categories
    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryService.getCategoriesAsc();
        model.addAttribute("categories", categories);
        return "category/categories";  // Refers to src/main/resources/templates/categories/list.html
    }

    // Show category form for creating or editing
    @GetMapping("/add-category")
    public String addCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/add-category";  // Refers to src/main/resources/templates/categories/form.html
    }

    // Save category (create or update)
    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    // Edit books by id (all filed together) and only admin can do- GET request
    @GetMapping("/edit-category/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category/edit-category";
    }

    @PostMapping("/edit-category/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategory(@PathVariable Long id,
                           @RequestParam("name") String name,
                           @RequestParam("description") String description
    ) {
        Category category = new Category(id, name, description);
        categoryService.updateCategory(category );
        return  "redirect:/categories";
    }

    // Delete category
    @GetMapping("/delete-category/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
        return "redirect:/categories";
    }
}
