package org.muyangj.bookhaven.services;

import org.muyangj.bookhaven.models.Inventory;
import org.muyangj.bookhaven.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory findById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory saveOrUpdateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    public List<Inventory> findByStockLessThan(int stockThreshold) {
        return inventoryRepository.findByStockLessThan(stockThreshold);
    }
}
