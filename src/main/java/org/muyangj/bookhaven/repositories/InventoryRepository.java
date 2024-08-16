package org.muyangj.bookhaven.repositories;

import org.muyangj.bookhaven.models.Book;
import org.muyangj.bookhaven.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByBookId(Long id);

    List<Inventory> findByStockLessThan(int stock);

    @Modifying
    @Query("UPDATE Inventory i SET i.customerEmailsForNotification = :emailsForNotification WHERE i.book.id = :bookId")
    void updateInventoryEmailsByBookId(Long bookId, List<String> emailsForNotification);
}

