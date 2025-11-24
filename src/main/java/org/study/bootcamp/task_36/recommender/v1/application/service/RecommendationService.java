package org.study.bootcamp.task_36.recommender.v1.application.service;

import org.study.bootcamp.task_36.recommender.v1.domain.model.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class RecommendationService {

    private final List<UserProfile> users;
    private final List<Product> products;
    private final List<ProductOrder> orders;
    private final Map<Integer, Product> productMap;
    private final Map<Integer, UserProfile> userMap;

    public RecommendationService(List<UserProfile> users, List<Product> products, List<ProductOrder> orders) {
        this.users = Objects.requireNonNullElseGet(users, List::of);
        this.products = Objects.requireNonNullElseGet(products, List::of);
        this.orders = Objects.requireNonNullElseGet(orders, List::of);
        this.productMap = this.products.stream()
                .collect(Collectors.toMap(
                        Product::productId, Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        this.userMap = this.users.stream()
                .collect(Collectors.toMap(
                        UserProfile::userId, Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public List<Product> recommendByInterests(int userId) {
        UserProfile user = userMap.get(userId);
        if (user == null) {
            return List.of();
        }

        Set<String> userInterests = user.interests().stream()
                .map(interest -> interest.toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return products.stream()
                .filter(product -> !Collections.disjoint(
                        product.tags().stream().map(tag -> tag.toLowerCase(Locale.ROOT)).toList(), userInterests))
                .sorted(Comparator
                        .comparingInt((Product product) -> countMatchingTags(product, userInterests)).reversed()
                        .thenComparing(Product::price)
                        .thenComparing(Product::name))
                .toList();
    }

    public List<Product> recommendPopularAmongSimilarUsers(int userId, int limit) {
        UserProfile currentUser = userMap.get(userId);
        if (currentUser == null) {
            return List.of();
        }
        int resultLimit = Math.max(0, limit);

        Set<Integer> similarUserIds = users.stream()
                .filter(user -> user.userId() != userId)
                .filter(user -> user.age() == currentUser.age()
                        && user.gender().equalsIgnoreCase(currentUser.gender())
                        && user.location().equalsIgnoreCase(currentUser.location()))
                .map(UserProfile::userId)
                .collect(Collectors.toSet());

        Map<Integer, Long> productPurchaseCount = orders.stream()
                .filter(order -> similarUserIds.contains(order.userId()))
                .collect(Collectors.groupingBy(ProductOrder::productId, Collectors.counting()));

        return productPurchaseCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(resultLimit)
                .map(entry -> productMap.get(entry.getKey()))
                .filter(Objects::nonNull)
                .toList();
    }

    public String discountCategoryForUser(int userId) {
        UserProfile user = userMap.get(userId);
        if (user == null) {
            return "No data!";
        }

        Set<String> userInterests = user.interests().stream()
                .map(interest -> interest.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        Map<String, Long> categoryPurchaseCount = orders.stream()
                .filter(order -> order.userId() == userId)
                .map(order -> productMap.get(order.productId()))
                .filter(Objects::nonNull)
                .filter(product -> product.tags().stream()
                        .map(tag -> tag.toLowerCase(Locale.ROOT))
                        .anyMatch(userInterests::contains))
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));

        return categoryPurchaseCount.entrySet().stream()
                .max(Map.Entry.<String, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                .map(Map.Entry::getKey)
                .orElse("There is no suitable category!");
    }

    private static int countMatchingTags(Product product, Set<String> userInterests) {
        int matchingCount = 0;
        for (String tag : product.tags()) {
            if (userInterests.contains(tag.toLowerCase(Locale.ROOT))) {
                matchingCount++;
            }
        }
        return matchingCount;
    }
}
