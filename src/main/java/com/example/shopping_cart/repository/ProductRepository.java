package com.example.shopping_cart.repository;

import com.example.shopping_cart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 依名稱模糊查詢
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // 查詢有庫存的商品
    List<Product> findByStockGreaterThan(Integer minStock);

    // 依價格範圍查詢
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    // 自訂 JPQL 查詢
    @Query("SELECT p FROM Product p WHERE p.stock > 0 ORDER BY p.price ASC")
    List<Product> findAvailableProductsSortedByPrice();

    // 使用 @Param 傳入參數的自訂查詢
    @Query("SELECT p FROM Product p WHERE p.price <= :maxPrice AND p.stock > 0")
    List<Product> findAffordableProducts(@Param("maxPrice") BigDecimal maxPrice);
}
