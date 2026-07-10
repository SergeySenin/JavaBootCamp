package org.study.bootcamp.mishustin.task40.food.delivery.v1.domain.model;

public enum FoodType {
    КУРИНЫЕ_КРЫЛЫШКИ("куриных крылышек"),
    ПИЦЦА("кусочков пиццы"),
    БУРГЕРЫ("бургеров"),
    ТАКО("тако"),
    СУШИ("роллов");

    private final String description;

    FoodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
