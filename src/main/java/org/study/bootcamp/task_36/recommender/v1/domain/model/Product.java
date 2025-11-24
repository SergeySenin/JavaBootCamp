package org.study.bootcamp.task_36.recommender.v1.domain.model;

import java.util.*;

public record Product(
        int productId,
        String name,
        String category,
        double price,
        List<String> tags
) {
    public Product {
        validateProductId(productId);
        validateName(name);
        validateCategory(category);
        validatePrice(price);

        name = name.trim();
        category = category.trim();
        tags = processTags(tags);
    }

    private static void validateProductId(int productId) {
        if (productId < 0) {
            throw new IllegalArgumentException("productId: must be >= 0");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null/blank");
        }
    }

    private static void validateCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category: must not be null/blank");
        }
    }

    private static void validatePrice(double price) {
        if (!Double.isFinite(price) || price < 0.0) {
            throw new IllegalArgumentException("price: must be finite and >= 0");
        }
    }

    private static List<String> processTags(List<String> tags) {
        if (tags == null) {
            return List.of();
        }

        return tags.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(tag -> !tag.isBlank())
                .toList();
    }
}
