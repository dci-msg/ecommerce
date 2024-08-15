package org.muyangj.bookhaven.config;

import org.muyangj.bookhaven.models.Category;
import org.muyangj.bookhaven.services.CategoryService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//When app run it check if category doesn't exist "Uncategorized" create
@Configuration
public class CategoryInitializer {

    private final CategoryService categoryService;

    public CategoryInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Bean
    public ApplicationRunner initializeCategories() {
        return args -> {
            String defaultCategoryName = "Uncategorized";
            if (categoryService.findCategoryByName(defaultCategoryName).isEmpty()) {
                Category defaultCategory = new Category(defaultCategoryName,
                        "This category is for items that have not been assigned to any specific category.");
                categoryService.addCategory(defaultCategory);
            }
        };
    }
}
