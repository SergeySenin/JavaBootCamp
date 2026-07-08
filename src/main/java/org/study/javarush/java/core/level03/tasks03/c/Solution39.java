package org.study.javarush.java.core.level03.tasks03.c;

import java.util.Scanner;

public class Solution39 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите ваш возраст:");
        int age = Integer.parseInt(console.nextLine());
        System.out.println("Введите код приглашения:");
        String code = console.nextLine().trim();
        if (age < 21) {
            System.out.println("К сожалению, только с 21 года.");
        } else {
            if (code.equals("VIP")) {
                System.out.println("Доступ к VIP-залу разрешен.");
            } else if (code.equals("GUEST")) {
                System.out.println("Гостевой доступ разрешен.");
            } else {
                System.out.println("Неверный код приглашения.");
            }
        }
    }
}
