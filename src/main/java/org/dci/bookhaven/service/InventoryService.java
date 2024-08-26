package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory findById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory findByBookId(Long bookId) {
        return inventoryRepository.findByBookId(bookId);
    }

    @Transactional
    public void addInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    public List<Inventory> findByStockLessThan(int stockThreshold) {
        return inventoryRepository.findByStockLessThan(stockThreshold);
    }

    @Transactional
    public void signUpForNotification(Long bookId, String email) {
        Inventory inventory = inventoryRepository.findByBookId(bookId);
        if (inventory != null && inventory.getStock() == 0) {
            inventory.getCustomerEmailsForNotification().add(email);
            inventoryRepository.updateInventoryEmailsByBookId(bookId, inventory.getCustomerEmailsForNotification());
        }
    }
}
