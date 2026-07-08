package org.study.javarush.java.core.level02.tasks02.f;

import java.util.Scanner;

public class Solution32 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int age = scanner.nextInt();
        System.out.println("Меня зовут " + name + ", мне " + age + " лет.");
    }
}
