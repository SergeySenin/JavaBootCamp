package org.study.javarush.java.core.level05.tasks05.c;

import java.util.Scanner;

public class Solution78 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        String ancientRiddle = console.nextLine();

        boolean isPalindrome = true;

        for (int leftIndex = 0; leftIndex < ancientRiddle.length() / 2; leftIndex++) {
            int rightIndex = ancientRiddle.length() - 1 - leftIndex;

            if (ancientRiddle.charAt(leftIndex) != ancientRiddle.charAt(rightIndex)) {
                isPalindrome = false;
                break;
            }
        }

        System.out.println(isPalindrome ? "YES" : "NO");
    }
}
