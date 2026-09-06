package org.study.javarush.java.core.level05.tasks05.d;

import java.util.Random;
import java.util.Scanner;

public class Solution82 {
    public static void main(String[] args) {

        Random random = new Random();
        Scanner console = new Scanner(System.in);

        int secretNumber = random.nextInt(100) + 1;

        while (true) {
            int userGuess = console.nextInt();

            if (userGuess < secretNumber) {
                System.out.println("Больше!");
            } else if (userGuess > secretNumber) {
                System.out.println("Меньше!");
            } else {
                System.out.println("Верно!");
                break;
            }
        }
    }
}
