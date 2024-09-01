package org.dci.bookhaven.config.data;

import org.dci.bookhaven.model.Category;
import org.dci.bookhaven.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(DataInitOrder.CATEGORY)
public class CategoryDataInitializer implements CommandLineRunner {

    private final CategoryService categoryService;

    public CategoryDataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        createCategory(
                "Uncategorized",
                "This category is for items that have not been assigned to any specific category."
        );
        createCategory(
                "Thriller",
                "Suspenseful tales with intense excitement, unexpected twists, and heart-pounding moments that keep you hooked."
        );
        createCategory(
                "Children",
                "Stories and adventures designed to spark imagination and nurture a love of reading in young minds."
        );
        createCategory(
                "History",
                "Explore the events and cultures that shaped our world, from ancient times to modern days."
        );
        createCategory(
                "Action",
                "High-octane adventures with fast-paced plots, thrilling sequences, and formidable heroes."
        );
        createCategory(
                "Sci-fi",
                "Futuristic worlds, alien civilizations, and mind-bending concepts that push the boundaries of imagination."
        );
        createCategory(
                "Crime",
                "Gripping stories of mystery and detective work that delve into the complexities of justice."
        );
        createCategory(
                "Novel",
                "A collection of narrative fiction offering deep characters, rich storytelling, and emotional depth."
        );
        createCategory(
                "Comics",
                "Stories come to life through dynamic illustrations, featuring superheroes, everyday heroes, and fantastical tales."
        );
    }

    private void createCategory(String name, String description) {
        if (categoryService.findCategoryByName(name).isEmpty()) {
            categoryService.addCategory(new Category(name, description));
        }
    }
}
