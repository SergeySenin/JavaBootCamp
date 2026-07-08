package org.study.bootcamp.task40.fooddelivery.v1.api.cli;

import org.study.bootcamp.task40.fooddelivery.v1.application.service.GriffinsFoodDelivery;

public class Demo {
    public static void main(String[] args) {

        String[] characterNames = {
                "Питер",
                "Лоис",
                "Мэг",
                "Крис",
                "Стьюи"
        };

        GriffinsFoodDelivery griffinsFoodDelivery = new GriffinsFoodDelivery(characterNames);

        griffinsFoodDelivery.startDelivery();
    }
}
