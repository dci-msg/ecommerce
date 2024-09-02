package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.LineItem;
import org.springframework.data.jpa.repository.Modifying;

import java.math.BigDecimal;

public interface LineItemService {
    @Modifying
    @Transactional
    void updateQuantity(Long lineItemId, int quantity);

    void addLineItem(LineItem lineItem);

    BigDecimal getLineTotal(LineItem lineItem);

    double getLineTotalById(Long id);

    LineItem getLineItemById(Long lineItemId);

    @Modifying
    @Transactional
    void deleteLineItemById(Long lineItemId);
}
