package org.study.bootcamp.task_8.amazon_warehouse.v1.domain.model;

public record Product(long id, String name, Category category) {

    public Product {
        validate(id, name, category);

        name = name.trim();
    }

    private static void validate(long id, String name, Category category) {
        if (id <= 0) {
            throw new IllegalArgumentException("id: must be positive");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null/blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("category: must not be null");
        }
    }

    @Override
    public String toString() {
        return "%s (%s, id=%d)".formatted(name, category, id);
    }
}
