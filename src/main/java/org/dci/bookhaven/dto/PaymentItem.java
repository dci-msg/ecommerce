package org.dci.bookhaven.dto;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

@Data
public class PaymentItem {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

}
