package org.study.javarush.java.core.level04.tasks;

import java.util.Scanner;

public class Solution68 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalSales = 0;
        while (true) {
            int sale = scanner.nextInt();
            if (sale < 0) {
                break;
            }
            totalSales += sale;
        }
        System.out.println(totalSales);
    }
}
