package org.muyangj.bookhaven.repositories;

import org.muyangj.bookhaven.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Find books by title
    List<Book> findByTitleContaining(String title);

    // Find books by category
    List<Book> findByCategoryName(String categoryName);

    // Find books by author
    List<Book> findByAuthor(String author);

    // Find book by ISBN
    Optional<Book> findByIsbn(String isbn);

    // Find all books by category
    List<Book> findByCategoryId(Long categoryId);

    @Modifying
    @Query("UPDATE Book b SET b.category.id = :defaultCategoryId WHERE b.id = :id")
    void updateCategoryIdById(Long id, Long defaultCategoryId);
}
