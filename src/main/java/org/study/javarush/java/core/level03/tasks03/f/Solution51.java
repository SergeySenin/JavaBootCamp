package org.study.javarush.java.core.level03.tasks03.f;

public class Solution51 {
    public static void main(String[] args) {
        int currentHour = 12;
        String greetingMessage = currentHour < 12 ? "Доброе утро" : "Добрый день";
        System.out.println(greetingMessage);
    }
}
