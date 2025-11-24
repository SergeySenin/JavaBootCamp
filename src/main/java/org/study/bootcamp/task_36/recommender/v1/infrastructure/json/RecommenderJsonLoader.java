package org.study.bootcamp.task_36.recommender.v1.infrastructure.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.study.bootcamp.task_36.recommender.v1.domain.model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class RecommenderJsonLoader {

    private RecommenderJsonLoader() {}

    private static final DateTimeFormatter SPACE_SEPARATED_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) -> {
                String dateTimeString = json.getAsString().trim();
                try {
                    return LocalDateTime.parse(dateTimeString, SPACE_SEPARATED_FORMATTER);
                } catch (Exception ignored) {
                    return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }
            })
            .create();

    public static List<UserProfile> loadUsersFromClasspath(String resourcePath) throws IOException {
        try (Reader reader = createReaderFromClasspath(resourcePath)) {
            Type userListType = new TypeToken<List<UserProfile>>() {}.getType();
            List<UserProfile> users = GSON.fromJson(reader, userListType);
            return users != null ? users : List.of();
        }
    }

    public static List<Product> loadProductsFromClasspath(String resourcePath) throws IOException {
        try (Reader reader = createReaderFromClasspath(resourcePath)) {
            Type productListType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = GSON.fromJson(reader, productListType);
            return products != null ? products : List.of();
        }
    }

    public static List<ProductOrder> loadOrdersFromClasspath(String resourcePath) throws IOException {
        try (Reader reader = createReaderFromClasspath(resourcePath)) {
            Type orderListType = new TypeToken<List<ProductOrder>>() {}.getType();
            List<ProductOrder> orders = GSON.fromJson(reader, orderListType);
            return orders != null ? orders : List.of();
        }
    }

    private static Reader createReaderFromClasspath(String resourcePath) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found on classpath: " + resourcePath);
        }
        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }
}
