package org.dci.bookhaven.service.impl;

import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.repository.LineItemRepository;
import org.dci.bookhaven.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineItemServiceImpl implements LineItemService {
    private LineItemRepository lineItemRepository;

    @Autowired
    public LineItemServiceImpl(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    @Override
    public void deleteLineItem(Long lineItemId) {
        lineItemRepository.deleteById(lineItemId);
    }

    @Override
    public void updateLineItemQuantity(Long lineItemId, int quantity) {
        lineItemRepository.findById(lineItemId).ifPresent(lineItem -> {
            lineItem.setQuantity(quantity);
            lineItemRepository.save(lineItem);
        });
    }

    @Override
    public void addLineItem(LineItem lineItem) {
        lineItemRepository.save(lineItem);
    }


}
