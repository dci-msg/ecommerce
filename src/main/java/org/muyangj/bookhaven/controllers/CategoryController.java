package org.muyangj.bookhaven.controllers;

import org.muyangj.bookhaven.models.Category;
import org.muyangj.bookhaven.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Show list of categories
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getCategoriesAsc();
        model.addAttribute("categories", categories);
        return "categories/list";  // Refers to src/main/resources/templates/categories/list.html
    }

    // Show category form for creating or editing
    @GetMapping("/form")
    public String showCategoryForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        Optional<Category> category = categoryService.findCategoryById(id);
        if(category.isEmpty()) {
        }
        model.addAttribute("category", category);
        return "categories/form";  // Refers to src/main/resources/templates/categories/form.html
    }

    // Save category (create or update)
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    // Delete category
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("name") String name) {
        categoryService.removeCategory(name);
        return "redirect:/categories";
    }
}
