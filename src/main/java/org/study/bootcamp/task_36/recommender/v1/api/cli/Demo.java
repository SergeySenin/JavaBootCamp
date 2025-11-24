package org.study.bootcamp.task_36.recommender.v1.api.cli;

import org.study.bootcamp.task_36.recommender.v1.application.service.*;
import org.study.bootcamp.task_36.recommender.v1.domain.model.*;
import org.study.bootcamp.task_36.recommender.v1.infrastructure.json.*;

import java.util.*;
import java.util.stream.*;

public class Demo {
    public static void main(String[] args) throws Exception {

        List<UserProfile> users = RecommenderJsonLoader.loadUsersFromClasspath("user_profiles.json");
        List<Product> products = RecommenderJsonLoader.loadProductsFromClasspath("products.json");
        List<ProductOrder> orders = RecommenderJsonLoader.loadOrdersFromClasspath("product_orders.json");

        System.out.printf("Loaded: users=%d, products=%d, orders=%d%n",
                users.size(), products.size(), orders.size());

        RecommendationService service = new RecommendationService(users, products, orders);

        Set<String> allProductTagsLowercased = products.stream()
                .flatMap(product -> product.tags().stream())
                .map(string -> string.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        int targetUserId = users.stream()
                .filter(userProfile -> userProfile.interests().stream()
                        .map(string -> string.toLowerCase(Locale.ROOT))
                        .anyMatch(allProductTagsLowercased::contains))
                .mapToInt(UserProfile::userId)
                .findFirst()
                .orElse(users.get(0).userId());

        System.out.println("\n=== Recommendations by interests for user " + targetUserId + " ===");
        List<Product> interestBasedRecommendations = service.recommendByInterests(targetUserId);
        if (interestBasedRecommendations.isEmpty()) {
            System.out.println("(no overlap between user interests and product tags)");
        } else {
            interestBasedRecommendations.forEach(product ->
                    System.out.printf("- %s (%s) $%.2f%n", product.name(), product.category(), product.price()));
        }

        System.out.println("\n=== Popular among similar users (top 5) ===");
        List<Product> popularAmongSimilarUsers = service.recommendPopularAmongSimilarUsers(targetUserId, 5);
        if (popularAmongSimilarUsers.isEmpty()) {
            System.out.println("(no users with the same gender, age, and location in the dataset)");
        } else {
            popularAmongSimilarUsers.forEach(product ->
                    System.out.printf("- %s [%s]%n", product.name(), product.category()));
        }

        System.out.println("\n=== Discount category suggestion ===");
        System.out.println("Category: " + service.discountCategoryForUser(targetUserId));
    }
}
