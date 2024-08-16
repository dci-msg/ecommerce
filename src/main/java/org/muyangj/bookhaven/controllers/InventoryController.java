package org.muyangj.bookhaven.controllers;

import org.muyangj.bookhaven.models.Inventory;
import org.muyangj.bookhaven.services.EmailService;
import org.muyangj.bookhaven.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookhaven")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private EmailService emailService;

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
        return "redirect:/bookhaven/inventories";
    }

    @GetMapping("/add-inventory")
    public String newInventory(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "inventory/inventory-form";
    }

    @PostMapping("/add-inventory")
    public String saveNewInventory(@ModelAttribute Inventory inventory) {
        inventoryService.addInventory(inventory);
       // return "redirect:/bookhaven/inventories";
        return "redirect:/bookhaven/notify";
    }

    @GetMapping("/delete-inventory/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteById(id);
        return "redirect:/bookhaven/inventories";
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
        return "redirect:/bookhaven/inventories";
    }

    @PostMapping("/signup-for-notification/{bookId}")
    public String signUpForNotification(@PathVariable Long bookId, @RequestParam String email) {
        inventoryService.signUpForNotification(bookId, email);
        return "redirect:/bookhaven/inventories";
    }
}
