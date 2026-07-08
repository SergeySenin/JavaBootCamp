package org.study.javarush.java.core.level04.tasks;

import java.util.Scanner;

public class Solution54 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("java")) {
            input = scanner.nextLine();
        }
        System.out.println("Смартфон разблокирован!");
    }
}
