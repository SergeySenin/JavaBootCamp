package org.study.bootcamp.task_8.amazon_warehouse.v2.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    enum Category {
        FOOD, ELECTRONICS, CLOTHING, OTHER
    }

    static class Product {
        int id;
        String name;
        Category category;
        private static int nextId = 1;

        public Product(String name, Category category) {
            this.id = nextId++;
            this.name = name;
            this.category = category;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class ProductManager {
        private Set<Product> products = new HashSet<>();

        public void addProduct(Category category, String name) {
            Product product = new Product(name, category);
            products.add(product);
            System.out.println("Добавлен: " + product + " в категорию " + category);
        }

        public void removeProduct(Category category, String name) {
            Product productToRemove = null;
            for (Product product : products) {
                if (product.category == category && product.name.equals(name)) {
                    productToRemove = product;
                    break;
                }
            }

            if (productToRemove != null) {
                products.remove(productToRemove);
                System.out.println("Удален: " + productToRemove);
            } else {
                System.out.println("Продукт не найден: " + name + " в категории " + category);
            }
        }

        public List<Product> findProductsByCategory(Category category) {
            List<Product> result = new ArrayList<>();
            for (Product product : products) {
                if (product.category == category) {
                    result.add(product);
                }
            }
            return result;
        }

        public Map<Category, List<Product>> groupProductsByCategory() {
            Map<Category, List<Product>> result = new HashMap<>();

            for (Product product : products) {
                result.computeIfAbsent(product.category, key -> new ArrayList<>()).add(product);
            }
            return result;
        }

        public void printAllProducts() {
            Map<Category, List<Product>> grouped = groupProductsByCategory();

            for (Category category : Category.values()) {
                List<Product> categoryProducts = grouped.getOrDefault(category, new ArrayList<>());

                System.out.println("Категория: " + category);
                System.out.println("Продукты:");
                if (categoryProducts.isEmpty()) {
                    System.out.println("- Нет продуктов");
                } else {
                    for (Product product : categoryProducts) {
                        System.out.println("- " + product.name);
                    }
                }
                System.out.println();
            }
        }

        public static void main(String[] args) {
            ProductManager manager = new ProductManager();

            manager.addProduct(Category.FOOD, "Apple");
            manager.addProduct(Category.FOOD, "Bread");
            manager.addProduct(Category.ELECTRONICS, "Laptop");
            manager.addProduct(Category.ELECTRONICS, "Smartphone");
            manager.addProduct(Category.CLOTHING, "T-Shirt");

            System.out.println("\n--- Все продукты ---");
            manager.printAllProducts();

            System.out.println("--- Поиск по категории FOOD ---");
            List<Product> foodProducts = manager.findProductsByCategory(Category.FOOD);
            System.out.println("Найдено: " + foodProducts);

            System.out.println("\n--- Удаление продукта ---");
            manager.removeProduct(Category.FOOD, "Apple");

            System.out.println("\n--- Все продукты после удаления ---");
            manager.printAllProducts();
        }
    }
}



/*
== для enum vs equals() для строк
1. == для enum (сравнение по ссылке)
java
enum Category { FOOD, ELECTRONICS, CLOTHING }

Category cat1 = Category.FOOD;
Category cat2 = Category.FOOD;

// Для enum можно и нужно использовать ==
if (cat1 == cat2) { // true - это один и тот же объект в памяти
    System.out.println("Категории равны");
}

Почему для enum безопасно ==:
Каждое значение enum - это один единственный объект в памяти (синглтон)
Category.FOOD всегда ссылается на один и тот же объект
== сравнивает ссылки, а для enum это безопасно

2. equals() для строк (сравнение по содержимому)
java
String str1 = "hello";
String str2 = new String("hello"); // новый объект в памяти

// НЕПРАВИЛЬНО для строк:
if (str1 == str2) { // false - разные объекты в памяти!
    System.out.println("Строки равны по == ");
}

// ПРАВИЛЬНО для строк:
if (str1.equals(str2)) { // true - содержимое одинаковое
    System.out.println("Строки равны по equals()");
}

Почему для строк нужно equals():
Строки могут быть разными объектами в памяти, но с одинаковым текстом
== сравнивает адреса в памяти, а equals() сравнивает символы
 */
