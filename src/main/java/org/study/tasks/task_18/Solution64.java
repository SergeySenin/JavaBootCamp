package org.study.tasks.task_18;

import java.util.Scanner;

public class Solution64 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password;
        do {
            password = scanner.nextLine();
        } while (password.length() < 6);
        System.out.println("Пароль надёжен, аккаунт создан!");
    }
}
