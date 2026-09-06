package org.study.javarush.java.core.level05.tasks05.b;

import java.util.Scanner;

public class Solution75 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        String magicMessage = console.nextLine();

        for (int index = magicMessage.length() - 1; index >= 0; index--) {
            System.out.print(magicMessage.charAt(index));
        }
    }
}
