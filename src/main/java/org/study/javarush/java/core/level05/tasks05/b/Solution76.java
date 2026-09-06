package org.study.javarush.java.core.level05.tasks05.b;

import java.util.Scanner;

public class Solution76 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        int magicLimit = console.nextInt();

        for (int number = 1; number <= magicLimit; number++) {
            System.out.println(number * number);
        }
    }
}
