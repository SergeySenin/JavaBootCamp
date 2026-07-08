package org.study.bootcamp.task40.fooddelivery.v1.domain.model;

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
