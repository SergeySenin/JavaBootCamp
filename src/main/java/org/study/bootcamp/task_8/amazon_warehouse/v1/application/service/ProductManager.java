package org.study.bootcamp.task_8.amazon_warehouse.v1.application.service;

import org.study.bootcamp.task_8.amazon_warehouse.v1.domain.model.Category;
import org.study.bootcamp.task_8.amazon_warehouse.v1.domain.model.Product;

import java.util.*;

public class ProductManager {

    private final Set<Product> products = new LinkedHashSet<>();
    private long nextId = 1L;

    public void addProduct(Category category, String name) {
        validate(category, name);

        Product product = new Product(nextId++, name.trim(), category);
        products.add(product);
    }

    public boolean removeProduct(Category category, String name) {
        if (category == null || name == null) {
            return false;
        }

        String targetName = name.trim();
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.category() == category && product.name().equals(targetName)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public List<Product> findProductsByCategory(Category category) {
        if (category == null) {
            return List.of();
        }

        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.category() == category) {
                result.add(product);
            }
        }
        return List.copyOf(result);
    }

    public Map<Category, List<Product>> groupProductsByCategory() {
        Map<Category, List<Product>> groupedByCategory = new LinkedHashMap<>();

        for (Product product : products) {
            groupedByCategory
                    .computeIfAbsent(product.category(), category -> new ArrayList<>())
                    .add(product);
        }
        return groupedByCategory;
    }

    public void printAllProducts() {
        Map<Category, List<Product>> groupedByCategory = groupProductsByCategory();

        if (groupedByCategory.isEmpty()) {
            System.out.println("No products.");
            return;
        }

        for (Category category : Category.values()) {
            List<Product> productsInCategory = groupedByCategory.get(category);
            if (productsInCategory == null || productsInCategory.isEmpty()) {
                continue;
            }

            System.out.println("Category: " + category);
            System.out.println("Products:");
            for (Product product : productsInCategory) {
                System.out.println("- " + product.name());
            }
            System.out.println();
        }
    }

    private static void validate(Category category, String name) {
        if (category == null) {
            throw new IllegalArgumentException("category: must not be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null/blank");
        }
    }
}
