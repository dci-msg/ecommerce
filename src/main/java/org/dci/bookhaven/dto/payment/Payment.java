package org.dci.bookhaven.dto.payment;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Payment {
    @SerializedName("items")
    PaymentItem[] items;

    public PaymentItem[] getItems() {
        return items;
    }
}
