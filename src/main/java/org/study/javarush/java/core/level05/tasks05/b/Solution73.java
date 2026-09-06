package org.study.javarush.java.core.level05.tasks05.b;

import java.util.Scanner;

public class Solution73 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        int ladderHeight = console.nextInt();

        for (int row = 1; row <= ladderHeight; row++) {
            for (int column = 1; column <= row; column++) {
                System.out.print("#");
            }

            System.out.println();
        }
    }
}
