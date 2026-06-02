package com.example.shopping_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCartRequest {

    @NotBlank(message = "顧客名稱不能為空")
    private String customerName;

    public CreateCartRequest() {}

    public CreateCartRequest(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}

class UpdateQuantityRequest {

    @NotNull(message = "數量不能為空")
    @Min(value = 1, message = "數量至少為 1")
    private Integer quantity;

    public UpdateQuantityRequest() {}

    public UpdateQuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}