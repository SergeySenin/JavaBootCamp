package org.study.javarush.java.core.level05.tasks05.c;

import java.util.Scanner;

public class Solution77 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        int pyramidHeight = console.nextInt();

        for (int row = 1; row <= pyramidHeight; row++) {
            int spaces = pyramidHeight - row;
            int stars = 2 * row - 1;

            for (int column = 1; column <= spaces; column++) {
                System.out.print(" ");
            }

            for (int column = 1; column <= stars; column++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }
}
