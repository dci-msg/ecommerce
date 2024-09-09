package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.LikedBook;
import org.dci.bookhaven.model.LikedBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikedBookRepository extends JpaRepository<LikedBook, LikedBookId> {
    List<LikedBook> findByUserId(Long userId);
    @Query("SELECT l.user.email FROM LikedBook l " +
            " JOIN User u " +
            " ON l.user.id = u.id" +
            " JOIN UserType ut " +
            " On u.userType.id = ut.id" +
            " WHERE ut.userTypeName = 'Customer' AND l.book.id = :bookId")
    List<String> findAllCustomersEmailByLikedBookId(Long bookId);
}
