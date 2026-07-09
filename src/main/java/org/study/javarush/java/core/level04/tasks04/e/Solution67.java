package org.study.javarush.java.core.level04.tasks04.e;

import java.util.Scanner;

public class Solution67 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        boolean isPrime = true;

        for (int divisor = 2; divisor <= n - 1; divisor++) {
            if (n % divisor == 0) {
                System.out.println(divisor);
                isPrime = false;
                break;
            }
        }

        if (isPrime) {
            System.out.println("Число простое");
        }
    }
}
