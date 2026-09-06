package org.study.javarush.java.core.level05.tasks05.d;

import java.util.Scanner;

public class Solution83 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        int dragonHealth = 50;
        int remainingHits = 10;

        while (remainingHits > 0 && dragonHealth > 0) {
            int hitPower = console.nextInt();

            dragonHealth -= hitPower;
            remainingHits--;

            if (dragonHealth <= 0) {
                System.out.println("Дракон повержен!");
                break;
            }
        }

        if (remainingHits == 0 && dragonHealth > 0) {
            System.out.println("Рыцарь проиграл!");
        }
    }
}
