package com.example.shopping_cart.controller;

import com.example.shopping_cart.dto.AddItemRequest;
import com.example.shopping_cart.dto.CreateCartRequest;
import com.example.shopping_cart.dto.UpdateItemRequest;
import com.example.shopping_cart.entity.Cart;
import com.example.shopping_cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@Valid @RequestBody CreateCartRequest request) {
        Cart cart = cartService.createCart(request.getCustomerName());
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Cart> addItem(
            @PathVariable Long id,
            @Valid @RequestBody AddItemRequest request) {
        Cart updated = cartService.addItem(id, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Cart> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateItemRequest request) {
        Cart updated = cartService.updateItemQuantity(cartId, itemId, request.getQuantity());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long cartId,
            @PathVariable Long itemId) {
        cartService.removeItem(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Cart> clearCart(@PathVariable Long id) {
        Cart cart = cartService.clearCart(id);
        return ResponseEntity.ok(cart);
    }
}
