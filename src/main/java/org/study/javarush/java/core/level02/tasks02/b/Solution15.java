package org.study.javarush.java.core.level02.tasks02.b;

public class Solution15 {
    public static void main(String[] args) {
        int prizes = 15;
        int teams = 4;

        int prizesPerTeam = prizes / teams;
        int remainingPrizes = prizes % teams;

        System.out.println(prizesPerTeam);
        System.out.println(remainingPrizes);
    }
}
