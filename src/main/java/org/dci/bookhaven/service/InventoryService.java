package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.repository.InventoryRepository;
import org.dci.bookhaven.repository.LikedBookRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final EmailService emailService;

    private final InventoryRepository inventoryRepository;

    private final UserRepository userRepository;

    private final LikedBookRepository likedBookRepository;

    public InventoryService(EmailService emailService, InventoryRepository inventoryRepository,
                            UserRepository userRepository,LikedBookRepository likedBookRepository) {
        this.emailService = emailService;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.likedBookRepository = likedBookRepository;
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
        Book book = inventory.getBook();
        if (stock == 0) {
            System.out.println("Notify customers for pre-order and notify admin about out of stock");
            for (String email : userRepository.findAllAdminEmails()) {
                System.out.println("Send email: + " + email);
                emailService.sendEmail(email,
                        "Book Out of Stock: " + book.getTitle(),
                        "The book \"" + book.getTitle() + "\" is out of stock. Please restock it as soon as possible.");
            }

            for (String email : likedBookRepository.findAllCustomersEmailByLikedBookId(book.getId())) {
                System.out.println("Send email: + " + email);
                emailService.sendEmail(email,
                        "Book Out of Stock: " + book.getTitle(),
                        "The book \"" + book.getTitle() + "\" is currently out of stock. You can sign up" +
                                " to be notified when it is back in stock.");
            }
        } else if (stock < 3) {
            System.out.println("Notify customers to buy the book");
            for (String email : likedBookRepository.findAllCustomersEmailByLikedBookId(book.getId())) {
                System.out.println("Send email: + " + email);
                emailService.sendEmail( email,
                        "Limited Stock Alert: " + book.getTitle(),
                        "Hurry! Only a few copies of \"" + book.getTitle() + "\" are left. Buy now before it’s too late!");
            }

            System.out.println("Notify admin order more books.");
            for (String email : userRepository.findAllAdminEmails()) {
                System.out.println("Send email: + " + email);
                emailService.sendEmail(email,
                        "Order more book: " + book.getTitle(),
                        "The book \"" + book.getTitle() + "\" is few. Please restock it as soon as possible.");
            }
        } else if(stock < 10) {
            System.out.println("Notify admin order more books.");
            for (String email : userRepository.findAllAdminEmails()) {
                System.out.println("Send email: + " + email);
                emailService.sendEmail(email,
                        "Order more book: " + book.getTitle(),
                        "The book \"" + book.getTitle() + "\" is few. Please restock it as soon as possible.");
            }
        }
    }
}
