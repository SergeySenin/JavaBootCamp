package org.study.javarush.java.core.level04.tasks04.f;

public class Solution71 {
    public static void main(String[] args) {
        int size = 8;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print("_");
                } else {
                    System.out.print("#");
                }
            }

            System.out.println();
        }
    }
}
