package org.study.javarush.java.core.level04.tasks04.f;

public class Solution70 {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print("⭐");

                if (j < 6) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
}
