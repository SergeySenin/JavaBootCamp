package org.study.javarush.java.core.level02.tasks;

public class Solution15 {
    public static void main(String[] args) {
        int prizes = 15, teams = 4;
        int prizesPerTeam = prizes / teams, remainingPrizes = prizes % teams;
        System.out.println(prizesPerTeam);
        System.out.println(remainingPrizes);
    }
}
