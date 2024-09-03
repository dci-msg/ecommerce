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
                "Children",
                "Stories and adventures designed to spark imagination and nurture a love of reading in young minds."
        );

        createCategory(
                "Classic",
                "This category is for timeless works of literature that have been widely recognized for their artistic and cultural value."
        );

        createCategory(
                "Dystopian",
                "This category includes books that explore societies characterized by poverty, oppression, and a totalitarian regime, often set in a speculative or future world."
        );

        createCategory(
                "Adventure",
                "Books in this category involve exciting journeys, exploration, and often feature daring heroes undertaking dangerous quests."
        );

        createCategory(
                "Romance",
                "This category is for books that focus on romantic relationships and love stories, often with emotional and passionate plots."
        );

        createCategory(
                "Historical",
                "Books in this category are set in a specific historical period, often focusing on historical figures, events, and cultural contexts."
        );

        createCategory(
                "Science Fiction",
                "Books in this category explore imaginative concepts such as futuristic science, advanced technology, and space exploration."
        );

        createCategory(
                "Psychological",
                "This category includes books that delve into the human mind, exploring psychological conflicts, mental health, and the complexities of human emotions."
        );
        
        createCategory(
                "Fantasy",
                "This category includes books that involve magical elements, mythical creatures, and supernatural phenomena, creating imaginary worlds and epic adventures."
        );
    }

    private void createCategory(String name, String description) {
        if (categoryService.findCategoryByName(name).isEmpty()) {
            categoryService.addCategory(new Category(name, description));
        }
    }
}
