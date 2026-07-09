package org.study.javarush.java.core.level03.tasks03.c;

import java.util.Scanner;

public class Solution37 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        int age = console.nextInt();
        console.nextLine();
        String secretCode = console.nextLine();

        if (age >= 18) {
            if (secretCode.equals("OPEN")) {
                System.out.println("Вход разрешен!");
            } else {
                System.out.println("Вам сегодня не повезло, вход запрещен.");
            }
        } else {
            System.out.println("Вам сегодня не повезло, вход запрещен.");
        }
    }
}
