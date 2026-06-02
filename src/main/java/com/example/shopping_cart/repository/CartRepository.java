package com.example.shopping_cart.repository;

import com.example.shopping_cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 依顧客名稱查詢購物車
    List<Cart> findByCustomerName(String customerName);
}
