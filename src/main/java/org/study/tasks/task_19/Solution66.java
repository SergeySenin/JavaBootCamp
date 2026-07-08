package org.study.tasks.task_19;

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
