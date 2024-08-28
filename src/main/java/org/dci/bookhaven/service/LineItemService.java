package org.dci.bookhaven.service;

import org.dci.bookhaven.model.LineItem;

public interface LineItemService {
    void deleteLineItem(Long lineItemId);

    void updateLineItemQuantity(Long lineItemId, int quantity);

    void addLineItem(LineItem lineItem);
}
