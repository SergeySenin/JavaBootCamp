package org.study.javarush.java.core.level02.tasks;

public class Solution28 {
    public static void main(String[] args) {
        int account1 = 100;
        int account2 = 200;
        account2 += account1;
        account1 = 0;
        account1 += 50;
        System.out.println(account1);
        System.out.println(account2);
    }
}
