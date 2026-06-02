package com.example.shopping_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateItemRequest {

    @NotNull(message = "數量不能為空")
    @Min(value = 1, message = "數量至少為 1")
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
