package org.study.javarush.java.core.level04.tasks04.f;

public class Solution72 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if (i + j == 13) {
                    System.out.println("Пароль найден: i=" + i + ", j=" + j);
                    return;
                }
            }
        }
    }
}
