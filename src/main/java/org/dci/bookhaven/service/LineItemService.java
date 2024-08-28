package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.LineItem;
import org.springframework.data.jpa.repository.Modifying;

public interface LineItemService {
    void deleteLineItem(Long lineItemId);

    @Modifying
    @Transactional
    void updateQuantity(Long lineItemId, int quantity);

    void addLineItem(LineItem lineItem);

    double getLineTotal(LineItem lineItem);

    double getLineTotal(Long id);

    LineItem getLineItemById(Long lineItemId);
}
