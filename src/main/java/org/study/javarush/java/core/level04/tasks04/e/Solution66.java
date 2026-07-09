package org.study.javarush.java.core.level04.tasks04.e;

public class Solution66 {
    public static void main(String[] args) {
        int[] productRatings = {3, -2, 7, -5, 8, 0};

        for (int rating : productRatings) {
            if (rating < 0) {
                continue;
            }

            System.out.println(rating);
        }
    }
}
