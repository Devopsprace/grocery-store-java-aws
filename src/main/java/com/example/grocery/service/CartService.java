package com.example.grocery.service;

import com.example.grocery.model.CartItem;
import com.example.grocery.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    public Map<Long, CartItem> createCart() {
        return new LinkedHashMap<>();
    }

    public void addToCart(Map<Long, CartItem> cart, Product product) {
        cart.compute(product.id(), (id, item) -> {
            if (item == null) {
                return new CartItem(product, 1);
            }
            item.addQuantity(1);
            return item;
        });
    }

    public void removeFromCart(Map<Long, CartItem> cart, Long productId) {
        cart.remove(productId);
    }

    public List<CartItem> items(Map<Long, CartItem> cart) {
        return new ArrayList<>(cart.values());
    }

    public BigDecimal total(Map<Long, CartItem> cart) {
        return cart.values().stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int count(Map<Long, CartItem> cart) {
        return cart.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public void clear(Map<Long, CartItem> cart) {
        cart.clear();
    }
}
