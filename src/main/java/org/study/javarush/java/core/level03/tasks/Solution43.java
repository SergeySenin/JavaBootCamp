package org.study.javarush.java.core.level03.tasks;

public class Solution43 {
    public static void main(String[] args) {
        boolean isSunny = true;
        boolean isWeekend = false;
        boolean canGoToPark = ((isSunny) && (isWeekend));
        boolean canStayHome = ((!isSunny) || (!isWeekend));
        System.out.println(canGoToPark);
        System.out.println(canStayHome);
    }
}
