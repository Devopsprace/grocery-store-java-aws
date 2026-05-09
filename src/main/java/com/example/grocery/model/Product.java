package com.example.grocery.model;

import java.math.BigDecimal;

public record Product(Long id, String name, String category, BigDecimal price, String unit, String description) {
}
