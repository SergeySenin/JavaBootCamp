package org.study.javarush.java.core.level05.tasks05.e;

import java.util.Random;
import java.util.Scanner;

public class Solution85 {
    public static void main(String[] args) {

        Random random = new Random();
        Scanner console = new Scanner(System.in);

        int knightHealth = 100;
        int dragonHealth = 50;
        int maxKnightHit = random.nextInt(19) + 2;

        while (knightHealth > 0 && dragonHealth > 0) {
            int knightHit = console.nextInt();

            if (knightHit <= maxKnightHit) {
                dragonHealth -= knightHit;
            }

            System.out.println("Жизни рыцаря: " + knightHealth);
            System.out.println("Жизни дракона: " + dragonHealth);

            if (dragonHealth <= 0) {
                System.out.println("Рыцарь победил!");
                break;
            }

            int firstDragonHit = random.nextInt(10) + 1;
            int secondDragonHit = random.nextInt(10) + 1;
            int dragonHit = firstDragonHit + secondDragonHit;

            knightHealth -= dragonHit;

            System.out.println("Жизни рыцаря: " + knightHealth);
            System.out.println("Жизни дракона: " + dragonHealth);

            if (knightHealth <= 0) {
                System.out.println("Дракон победил!");
                break;
            }
        }
    }
}
