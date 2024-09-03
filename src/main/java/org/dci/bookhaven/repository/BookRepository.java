package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query("UPDATE Book b SET b.category.id = :defaultCategoryId WHERE b.id = :id")
    void updateCategoryIdById(Long id, Long defaultCategoryId);

    @Query("SELECT b FROM Book b WHERE " +
           " (:language IS NULL OR b.language = :language)" +
            " AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            " AND (:keyword IS NULL OR (LOWER(b.title) LIKE :keyword ) " +
            " OR (LOWER(b.author) LIKE :keyword) " +
            " OR (LOWER(b.isbn) LIKE :keyword)) " +
            " AND (:priceCriteria IS NULL OR (b.price < 5 AND :priceCriteria = 'Below 5') " +
            " OR (b.price BETWEEN 5 AND 10 AND :priceCriteria = '5 - 10') " +
            " OR (b.price > 10 AND :priceCriteria = 'Above 10')) "

    )
    List<Book> findByKeyWordAndCategoryAndPriceAndLanguage(String keyword, Long categoryId, String priceCriteria,
                                                           String language);


    List<Book> findByAuthorContainingOrTitleContainingOrIsbnContainingOrDescriptionContaining(String author,
                                                                                              String title,
                                                                                              String isbn,
                                                                                              String description);

    List<Book> findByTitle(String title);
}
