package org.study.javarush.java.core.level05.tasks05.f;

public class Solution91 {
    public static void main(String[] args) {

        for (int row = 1; row <= 10; row++) {
            for (int column = 1; column <= 10; column++) {
                System.out.printf("%-4d", row * column);
            }

            System.out.println();
        }
    }
}
