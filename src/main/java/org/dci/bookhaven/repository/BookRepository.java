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
           " :language IS NULL OR LOWER(b.language) = LOWER(:language)" +
            " AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            " AND (:keyword IS NULL OR LOWER(b.title) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            " OR LOWER(b.author) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            " OR LOWER(b.isbn) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            " AND (:priceCriteria IS NULL OR (b.price < 500 AND :priceCriteria = 'Below 500') " +
            " OR (b.price BETWEEN 500 AND 1000 AND :priceCriteria = '500 - 1000') " +
            " OR (b.price > 1000 AND :priceCriteria = 'Above 1000')) "

    )
    List<Book> findByKeyWordAndCategoryAndPriceAndLanguage(String keyword, Long categoryId, String priceCriteria,
                                                           String language);


    List<Book> findByAuthorContainingOrTitleContainingOrIsbnContainingOrDescriptionContaining(String author,
                                                                                              String title,
                                                                                              String isbn,
                                                                                              String description);
}
