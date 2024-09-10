package org.dci.bookhaven.service.impl;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.repository.LineItemRepository;
import org.dci.bookhaven.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LineItemServiceImpl implements LineItemService {
    private LineItemRepository lineItemRepository;

    @Autowired
    public LineItemServiceImpl(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }


    @Modifying
    @Transactional
    @Override
    public void updateQuantity(Long lineItemId, int quantity) {
        lineItemRepository.findById(lineItemId).ifPresent(lineItem -> {
            lineItem.setQuantity(quantity);
            lineItemRepository.save(lineItem);
        });
    }

    @Modifying
    @Transactional
    @Override
    public void addLineItem(LineItem lineItem) {
        lineItemRepository.save(lineItem);
    }

    @Override
    public BigDecimal getLineTotal(LineItem lineItem) {
        return lineItem.getBook().getPrice().multiply(BigDecimal.valueOf(lineItem.getQuantity()));
    }

    @Override
    public double getLineTotalById(Long id){
        LineItem lineItem = lineItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Line item not found"));
        return lineItem.getBook().getPrice().doubleValue() * lineItem.getQuantity();
    }


    @Override
    public LineItem getLineItemById(Long lineItemId) {
        return lineItemRepository.findById(lineItemId).orElseThrow(() -> new RuntimeException("Line item not found"));
    }

    @Modifying
    @Transactional
    @Override
    public void deleteLineItemById(Long lineItemId) {
        lineItemRepository.deleteById(lineItemId);
    }

    @Modifying
    @Transactional
    @Override
    public void create(LineItem lineItem) {
        lineItemRepository.save(lineItem);
    }

    @Override
    public List<LineItem> getLineItems() {
        return lineItemRepository.findAll();
    }


}
