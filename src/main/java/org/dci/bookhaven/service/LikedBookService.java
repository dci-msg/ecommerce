package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.LikedBook;
import org.dci.bookhaven.model.LikedBookId;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.repository.BookRepository;
import org.dci.bookhaven.repository.LikedBookRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LikedBookService {
    private final LikedBookRepository likedBookRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;


    public LikedBookService(LikedBookRepository likedBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.likedBookRepository = likedBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addLikedBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found userId = " + userId));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Not found userId = " + bookId));

        likedBookRepository.save(new LikedBook(new LikedBookId(userId, bookId), user, book));
    }

    @Transactional
    public void deleteLikedBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found userId = " + userId));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Not found userId = " + bookId));

        likedBookRepository.deleteById(new LikedBookId(userId, bookId));
    }

    public Integer likedBooksCount(Long userId) {
        return likedBooks(userId).size();
    }

    public Set<Book> likedBooks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found userId = " + userId));

        return likedBookRepository.findByUserId(userId).stream().map(LikedBook::getBook).collect(Collectors.toSet());
    }

    public Set<Long> likedBookIds(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found userId = " + userId));

        return likedBookRepository.findByUserId(userId).stream().map(LikedBook::getBook).map(Book::getId).collect(Collectors.toSet());
    }
}
