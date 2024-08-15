package org.muyangj.bookhaven.services;

import jakarta.transaction.Transactional;
import org.muyangj.bookhaven.models.Book;
import org.muyangj.bookhaven.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    }

    @Transactional
    public void updateBook(Book book) {
        Long id = book.getId();
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Not found id = " + id);
        }
        System.out.println(book);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Not found id = " + id);
        }
        bookRepository.deleteById(id);
    }
}
