package org.study.javarush.java.core.level04.tasks04.d;

import java.util.Scanner;

public class Solution62 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pinCode;

        do {
            pinCode = scanner.nextInt();
        } while (pinCode < 1000 || pinCode > 9999);

        System.out.println("PIN-код принят");
    }
}
