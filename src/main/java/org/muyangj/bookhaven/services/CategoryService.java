package org.muyangj.bookhaven.services;

import jakarta.transaction.Transactional;
import org.muyangj.bookhaven.models.Category;
import org.muyangj.bookhaven.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category addCategory(Category category) {
        String name = category.getName();

        if (isExistCategoryByName(name)) {
            throw new RuntimeException("Already exist name = " + name);
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public Category editCategory(Category category) {
        String name = category.getName();

        if (!isExistCategoryByName(name)) {
            throw new RuntimeException("Not found name = " + name);
        }
        return categoryRepository.save(category);
    }


    @Transactional
    public void removeCategory(String name) {
        if (!isExistCategoryByName(name)) {
            throw new RuntimeException("Not found name = " + name);
        }
        categoryRepository.deleteByName(name);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new RuntimeException("Not found name = " + name));
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    private Boolean isExistCategoryByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    public List<Category> getCategoriesAsc() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
}
