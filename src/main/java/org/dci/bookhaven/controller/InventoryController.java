package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.service.EmailService;
import org.dci.bookhaven.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InventoryController {

    private InventoryService inventoryService;

    private EmailService emailService;

    public InventoryController(InventoryService inventoryService, EmailService emailService) {
        this.inventoryService = inventoryService;
        this.emailService = emailService;
    }

    @GetMapping("/inventories")
    public String inventories(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory/inventories";
    }

    @GetMapping("/edit-inventory/{id}")
    public String editInventoryForm(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.findById(id);
        model.addAttribute("inventory", inventory);
        return "inventory/inventory-form";
    }

    @PostMapping("/edit-inventory/{id}")
    public String editInventory(@PathVariable Long id, @ModelAttribute Inventory inventory) {
        Inventory existingInventory = inventoryService.findById(id);
        if (existingInventory != null) {
            existingInventory.setStock(inventory.getStock());
            inventoryService.addInventory(existingInventory);
        }
        notifyAdminAndCustomers();
        return "redirect:/inventories";
    }

    @GetMapping("/add-inventory")
    public String newInventory(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "inventory/inventory-form";
    }

    @PostMapping("/add-inventory")
    public String saveNewInventory(@ModelAttribute Inventory inventory) {
        inventoryService.addInventory(inventory);
        return "redirect:/notify";
    }

    @GetMapping("/delete-inventory/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteById(id);
        return "redirect:/inventories";
    }

    @PostMapping("/notify")
    public String notifyAdminAndCustomers() {
        List<Inventory> lowStockInventories = inventoryService.findByStockLessThan(10);
        for (Inventory inventory : lowStockInventories) {
            System.out.println(" Notify admin order more books.");
            emailService.sendAdminNotification(inventory.getBook());
            if (inventory.getStock() < 3) {
                System.out.println("Notify customers to buy the book");
                emailService.sendCustomerNotification(inventory.getBook());
            } else if (inventory.getStock() == 0) {
                System.out.println(" Notify customers for pre-order and notify admin about out of stock");
                emailService.sendAdminNotification(inventory.getBook());
                emailService.sendCustomerOutOfStockNotification(inventory.getBook());
            }
        }
        return "redirect:/inventories";
    }

    @PostMapping("/signup-for-notification/{bookId}")
    public String signUpForNotification(@PathVariable Long bookId, @RequestParam String email) {
        inventoryService.signUpForNotification(bookId, email);
        return "redirect:/inventories";
    }
}
