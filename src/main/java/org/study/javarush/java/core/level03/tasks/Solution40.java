package org.study.javarush.java.core.level03.tasks;

import java.util.Scanner;

public class Solution40 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите ваш возраст:");
        int age = console.nextInt();
        console.nextLine();
        System.out.println("У вас есть клубная карта? (да/нет):");
        String hasClubCard = console.nextLine().trim().toLowerCase();
        if (age >= 65) {
            System.out.println("Ваша скидка: 20%.");
        } else {
            if (hasClubCard.equals("да")) {
                System.out.println("Ваша скидка: 10%.");
            } else {
                System.out.println("Скидки нет.");
            }
        }
    }
}
