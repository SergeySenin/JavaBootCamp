package org.study.javarush.java.core.level03.tasks;

public class Solution33 {
    public static void main(String[] args) {
        int visitorAge = 17;
        if (visitorAge < 18) {
            System.out.println("Доступ запрещен");
        } else {
            System.out.println("Добро пожаловать!");
        }
    }
}
