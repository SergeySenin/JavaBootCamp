package org.study.javarush.java.core.level05.tasks05.f;

public class Solution90 {
    public static void main(String[] args) {

        int boardSize = 8;

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if ((row + column) % 2 == 0) {
                    System.out.print("##");
                } else {
                    System.out.print("__");
                }
            }

            System.out.println();
        }
    }
}
