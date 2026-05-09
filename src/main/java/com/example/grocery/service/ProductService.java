package com.example.grocery.service;

import com.example.grocery.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final List<Product> products = List.of(
            new Product(1L, "Tomato", "Vegetables", new BigDecimal("30.00"), "1 kg", "Fresh farm tomatoes"),
            new Product(2L, "Potato", "Vegetables", new BigDecimal("25.00"), "1 kg", "Daily cooking essential"),
            new Product(3L, "Onion", "Vegetables", new BigDecimal("35.00"), "1 kg", "Premium red onions"),
            new Product(4L, "Milk", "Dairy", new BigDecimal("28.00"), "500 ml", "Full cream milk"),
            new Product(5L, "Bread", "Bakery", new BigDecimal("40.00"), "1 pack", "Whole wheat bread"),
            new Product(6L, "Eggs", "Dairy", new BigDecimal("72.00"), "12 pcs", "Farm fresh eggs"),
            new Product(7L, "Rice", "Grains", new BigDecimal("65.00"), "1 kg", "Premium basmati rice"),
            new Product(8L, "Apple", "Fruits", new BigDecimal("120.00"), "1 kg", "Sweet and crispy apples")
    );

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream().filter(product -> product.id().equals(id)).findFirst();
    }
}
