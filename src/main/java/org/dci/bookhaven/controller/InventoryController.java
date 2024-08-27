package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Inventory;
import org.dci.bookhaven.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
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
        inventoryService.editInventory(id, inventory.getStock());
        inventoryService.notifyAdminAndCustomers(id);
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
        inventoryService.deleteInventory(id);
        return "redirect:/inventories";
    }

    @PostMapping("/signup-for-notification/{bookId}")
    public String signUpForNotification(@PathVariable Long bookId, @RequestParam String email) {
        inventoryService.signUpForNotification(bookId, email);
        return "redirect:/inventories";
    }
}
