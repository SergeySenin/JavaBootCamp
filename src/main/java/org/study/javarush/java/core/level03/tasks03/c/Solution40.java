package org.study.javarush.java.core.level03.tasks03.c;

import java.util.Scanner;

public class Solution40 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        int age = console.nextInt();
        console.nextLine();
        String hasClubCard = console.nextLine();

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
