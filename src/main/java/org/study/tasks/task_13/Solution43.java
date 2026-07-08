package org.study.tasks.task_13;

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
