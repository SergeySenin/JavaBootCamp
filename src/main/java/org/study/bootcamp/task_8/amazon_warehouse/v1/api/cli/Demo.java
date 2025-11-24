package org.study.bootcamp.task_8.amazon_warehouse.v1.api.cli;

import org.study.bootcamp.task_8.amazon_warehouse.v1.domain.model.Category;
import org.study.bootcamp.task_8.amazon_warehouse.v1.domain.model.Product;
import org.study.bootcamp.task_8.amazon_warehouse.v1.application.service.ProductManager;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        ProductManager manager = new ProductManager();

        manager.addProduct(Category.FOOD, "Apple");
        manager.addProduct(Category.FOOD, "Bread");
        manager.addProduct(Category.ELECTRONICS, "Laptop");
        manager.addProduct(Category.ELECTRONICS, "Smartphone");
        manager.addProduct(Category.CLOTHING, "T-shirt");
        manager.addProduct(Category.OTHER, "Book");

        manager.printAllProducts();

        List<Product> electronics = manager.findProductsByCategory(Category.ELECTRONICS);
        System.out.println("Found in ELECTRONICS: " + electronics);

        boolean removed = manager.removeProduct(Category.FOOD, "Apple");
        System.out.println("\nRemoved Apple from FOOD: " + removed);

        System.out.println("\nAfter removal:");
        manager.printAllProducts();
    }
}
