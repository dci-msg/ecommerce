package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.repository.BookRepository;
import org.dci.bookhaven.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final InventoryRepository inventoryRepository;

    public BookService(BookRepository bookRepository, InventoryRepository inventoryRepository) {
        this.bookRepository = bookRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found id = " + id));
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("invalid book object");
        }

        bookRepository.save(book);
        Inventory inventory = new Inventory(book, 0);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void updateBook(Book book) {
        Long id = book.getId();
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Not found id = " + id);
        }

        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Not found id = " + id);
        }
        inventoryRepository.deleteById(inventoryRepository.findByBookId(id).getId());
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query) {
        if (query.isBlank()) {
            return bookRepository.findAll();
        }
        return bookRepository.findByAuthorContainingOrTitleContainingOrIsbnContainingOrDescriptionContaining(query,
                query, query, query);
    }

    public List<Book> getBooks(String keyword, Long categoryId, String priceCriteria, String language) {
        System.out.println("server");

        if (keyword.isBlank() && categoryId == null && priceCriteria.isBlank() && language.isBlank()) {
            return bookRepository.findAll();
        }

        if (keyword.isBlank()) {
            keyword = null;
        } else {
            keyword = "%" + keyword.toLowerCase() + "%";
        }

        if (priceCriteria.isBlank()) {
            priceCriteria = null;
        }

        if (language.isBlank()) {
            language = null;
        }

        System.out.println("keyWord:" + keyword);
        System.out.println("categoryId:" + categoryId);
        System.out.println("priceCriteria:" + priceCriteria);
        System.out.println("language:" + language);
        return bookRepository.findByKeyWordAndCategoryAndPriceAndLanguage(keyword, categoryId, priceCriteria, language);
    }
}
