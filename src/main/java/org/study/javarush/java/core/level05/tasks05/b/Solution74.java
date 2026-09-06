package org.study.javarush.java.core.level05.tasks05.b;

import java.util.Scanner;

public class Solution74 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        int number = console.nextInt();

        if (number <= 1) {
            System.out.println("NO");
            return;
        }

        boolean isPrime = true;

        for (int divisor = 2; divisor < number; divisor++) {
            if (number % divisor == 0) {
                isPrime = false;
                break;
            }
        }

        System.out.println(isPrime ? "YES" : "NO");
    }
}
