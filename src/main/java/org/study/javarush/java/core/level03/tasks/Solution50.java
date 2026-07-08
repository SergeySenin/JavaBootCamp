package org.study.javarush.java.core.level03.tasks;

public class Solution50 {
    public static void main(String[] args) {
        int orderNumber = 665;
        String orderStatus = orderNumber % 2 == 0 ? "Чётный" : "Нечётный";
        System.out.println(orderStatus);
    }
}
