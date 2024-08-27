package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.repository.InventoryRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final EmailService emailService;

    private final InventoryRepository inventoryRepository;

    private final NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    public InventoryService(EmailService emailService, InventoryRepository inventoryRepository, NotificationService notificationService) {
        this.emailService = emailService;
        this.inventoryRepository = inventoryRepository;
        this.notificationService = notificationService;
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
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException("Not found id = " + id);
        }
        inventoryRepository.deleteById(id);
    }

    @Transactional
    public void editInventory(Long id, Integer stock) {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found id = " + id));
        existingInventory.setStock(stock);
        inventoryRepository.save(existingInventory);
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

    public void notifyAdminAndCustomers(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found id = " + id));
        int stock = inventory.getStock();
        if (stock == 0) {
            //userRepository.findAllAdmin()
            //notificationService.sendEmail(inventory.getBook());
            System.out.println("Notify customers for pre-order and notify admin about out of stock");
            emailService.sendAdminNotification(inventory.getBook());
            emailService.sendCustomerOutOfStockNotification(inventory.getBook());
        } else if (stock < 10) {
            System.out.println("Notify admin order more books.");
            emailService.sendAdminNotification(inventory.getBook());
        }
        if (stock < 3) {
            System.out.println("Notify customers to buy the book");
            emailService.sendCustomerNotification(inventory.getBook());
        }
    }
}
