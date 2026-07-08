package org.study.javarush.java.core.level04.tasks04.d;

import java.util.Scanner;

public class Solution63 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            System.out.println("Меню:");
            System.out.println("1. Вывести приветствие");
            System.out.println("2. Выйти");
            number = scanner.nextInt();
            if (number == 1) {
                System.out.println("Привет!");
            }
        } while (number != 2);
    }
}
