package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.repository.BookRepository;
import org.dci.bookhaven.repository.InventoryRepository;
import org.springframework.stereotype.Service;

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

    public List<Book> getBooksByCategoryName(String categoryName) {
        return bookRepository.findByCategoryName(categoryName);
    }

    public List<Book> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Not found isbn = " + isbn));
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
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
        if (query == null || query.isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.findByAuthorContainingOrTitleContainingOrIsbnContainingOrDescriptionContaining(query, query, query, query);
    }
}
