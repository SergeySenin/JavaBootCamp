package org.study.javarush.java.core.level03.tasks03.f;

public class Solution50 {
    public static void main(String[] args) {
        int orderNumber = 665;

        String orderStatus = orderNumber % 2 == 0 ? "Чётный" : "Нечётный";

        System.out.println(orderStatus);
    }
}
