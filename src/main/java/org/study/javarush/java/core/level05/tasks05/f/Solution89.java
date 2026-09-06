package org.study.javarush.java.core.level05.tasks05.f;

public class Solution89 {
    public static void main(String[] args) {

        int currentNumber = 1;

        while (true) {
            if (currentNumber % 7 == 0) {
                System.out.println("Первое число, кратное 7: " + currentNumber);
                break;
            }

            currentNumber++;
        }
    }
}
