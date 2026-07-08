package org.study.javarush.java.core.level04.tasks04.b;

import java.util.Scanner;

public class Solution56 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sumOfCoffeePrices = 0;
        int costOfCoffee = scanner.nextInt();
        while (costOfCoffee >= 0) {
            sumOfCoffeePrices += costOfCoffee;
            costOfCoffee = scanner.nextInt();
        }
        System.out.println(sumOfCoffeePrices);
    }
}
