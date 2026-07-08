package org.study.tasks.task_18;

import java.util.Scanner;

public class Solution62 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pinCode;
        do {
            pinCode = scanner.nextInt();
        } while (!(1000 <= pinCode && pinCode <= 9999));
        System.out.println("PIN-код принят");
    }
}
