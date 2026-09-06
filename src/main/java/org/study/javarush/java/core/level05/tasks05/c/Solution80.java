package org.study.javarush.java.core.level05.tasks05.c;

import java.util.Scanner;

public class Solution80 {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        int pathOne = console.nextInt();
        int pathTwo = console.nextInt();
        int pathThree = console.nextInt();

        int shortestPath = pathOne;

        if (pathTwo < shortestPath) {
            shortestPath = pathTwo;
        }

        if (pathThree < shortestPath) {
            shortestPath = pathThree;
        }

        System.out.println(shortestPath);
    }
}
