package org.study.javarush.java.core.level03.tasks;

public class Solution49 {
    public static void main(String[] args) {
        int alexsTime = 7;
        int mikesTime = 9;
        int fastestTime = alexsTime < mikesTime ? alexsTime : mikesTime;
        System.out.println(fastestTime);
    }
}
