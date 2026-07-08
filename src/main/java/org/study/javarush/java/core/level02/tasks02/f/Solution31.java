package org.study.javarush.java.core.level02.tasks02.f;

import java.util.Scanner;

public class Solution31 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int firstPrice = scanner.nextInt();
        int secondPrice = scanner.nextInt();
        int totalPrice = firstPrice + secondPrice;
        System.out.println(totalPrice);
    }
}
