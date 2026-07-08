package org.study.javarush.java.core.level03.tasks;

import java.util.Scanner;

public class Solution38 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите ваш возраст:");
        int age = console.nextInt();
        console.nextLine();
        System.out.println("Введите ваш город:");
        String city = console.nextLine();
        if (age >= 21) {
            if (city.equals("Минск")) {
                System.out.println("Добро пожаловать в клуб, Минск!");
            } else {
                System.out.println("Извините, вход запрещен.");
            }
        } else {
            System.out.println("Извините, вход запрещен.");
        }
    }
}
