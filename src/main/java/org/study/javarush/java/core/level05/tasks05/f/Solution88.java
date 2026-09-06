package org.study.javarush.java.core.level05.tasks05.f;

public class Solution88 {
    public static void main(String[] args) {

        for (int itemNumber = 1; itemNumber <= 20; itemNumber++) {
            if (itemNumber % 3 == 0) {
                continue;
            }

            System.out.print(itemNumber);

            if (itemNumber < 20) {
                System.out.print(" ");
            }
        }
    }
}
