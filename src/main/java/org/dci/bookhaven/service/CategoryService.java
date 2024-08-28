package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Category;
import org.dci.bookhaven.repository.BookRepository;
import org.dci.bookhaven.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final BookRepository bookRepository;

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addCategory(Category category) {
        String name = category.getName();

        if (isExistCategoryByName(name)) {
            throw new RuntimeException("Already exist name = " + name);
        }

        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(Category category) {
        Long id = category.getId();

        findCategoryById(id).orElseThrow(() -> new RuntimeException("Not found id = " + id));
        categoryRepository.save(category);
    }

    @Transactional
    public void removeCategory(Long id) {
        Category category = findCategoryById(id).orElseThrow(() -> new RuntimeException("Not found id = " + id));
        Category defaultCategory = findCategoryByName("Uncategorized").orElseThrow(() -> new RuntimeException("Not found id = " + id));
        List<Book> books = category.getBooks();
        for (Book book : books) {
            bookRepository.updateCategoryIdById(book.getId(), defaultCategory.getId());
        }
        categoryRepository.deleteById(id);
    }

    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found id = " + id));
    }

    private Boolean isExistCategoryByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    public List<Category> getCategoriesAsc() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
}
