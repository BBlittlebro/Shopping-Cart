package com.example.shopping_cart.service;

import com.example.shopping_cart.entity.Cart;
import com.example.shopping_cart.entity.CartItem;
import com.example.shopping_cart.entity.Product;
import com.example.shopping_cart.exception.ResourceNotFoundException;
import com.example.shopping_cart.repository.CartItemRepository;
import com.example.shopping_cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    // CRUD 基本操作
    @Transactional
    public Cart createCart(String customerName) {
        Cart cart = new Cart(customerName);
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", id));
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // 購物車商品操作
    @Transactional
    public Cart addItem(Long cartId, Long productId, Integer quantity) {
        Cart cart = getCartById(cartId);
        Product product = productService.getProductById(productId);

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("庫存不足！目前庫存：" + product.getStock());
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateItemQuantity(Long cartId, Long itemId, Integer newQuantity) {
        Cart cart = getCartById(cartId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", itemId));

        if (item.getProduct().getStock() < newQuantity) {
            throw new IllegalArgumentException("庫存不足！");
        }

        item.setQuantity(newQuantity);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItem(Long cartId, Long itemId) {
        Cart cart = getCartById(cartId);

        CartItem toRemove = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", itemId));

        cart.getItems().remove(toRemove);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cart", id);
        }
        cartRepository.deleteById(id);
    }
}
