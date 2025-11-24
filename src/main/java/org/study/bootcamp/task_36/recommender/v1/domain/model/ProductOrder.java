package org.study.bootcamp.task_36.recommender.v1.domain.model;

import java.time.LocalDateTime;

public record ProductOrder(
        int userId,
        int productId,
        LocalDateTime orderDate
) {
    public ProductOrder {
        validateUserId(userId);
        validateProductId(productId);
        validateOrderDate(orderDate);
    }

    private static void validateUserId(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("userId: must be >= 0");
        }
    }

    private static void validateProductId(int productId) {
        if (productId < 0) {
            throw new IllegalArgumentException("productId: must be >= 0");
        }
    }

    private static void validateOrderDate(LocalDateTime orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("orderDate: must not be null");
        }
    }
}
