package org.study.javarush.java.core.level04.tasks04.f;

public class Solution69 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(i + "," + j);

                if (j < 3) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
}
