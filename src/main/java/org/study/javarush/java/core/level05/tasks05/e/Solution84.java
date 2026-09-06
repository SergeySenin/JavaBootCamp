package org.study.javarush.java.core.level05.tasks05.e;

import java.util.Random;
import java.util.Scanner;

public class Solution84 {
    public static void main(String[] args) {

        Random random = new Random();
        Scanner console = new Scanner(System.in);

        int stones = random.nextInt(100) + 1;

        while (stones > 0) {
            int playerStones = console.nextInt();

            if (playerStones > stones) {
                playerStones = stones;
            }

            stones -= playerStones;
            System.out.println("Осталось камней: " + stones);

            if (stones == 0) {
                System.out.println("Игрок победил!");
                break;
            }

            int golemStones = random.nextInt(Math.min(10, stones)) + 1;

            stones -= golemStones;
            System.out.println("Осталось камней: " + stones);

            if (stones == 0) {
                System.out.println("Голем победил!");
                break;
            }
        }
    }
}
