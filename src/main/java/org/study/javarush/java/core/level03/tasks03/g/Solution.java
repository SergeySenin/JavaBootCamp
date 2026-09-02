package org.study.javarush.java.core.level03.tasks03.g;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        String customerNameInput = console.nextLine();
        String accountStatusInput = console.nextLine();
        String deviceStatusInput = console.nextLine();
        String confirmationCodeInput = console.nextLine();

        int accountBalance = console.nextInt();
        int transferAmount = console.nextInt();
        int accountAgeDays = console.nextInt();
        int failedLoginAttempts = console.nextInt();

        String customerName = customerNameInput.trim();

        boolean isAccountActive = accountStatusInput.equals("ACTIVE");
        boolean isTrustedDevice = deviceStatusInput.equals("TRUSTED");
        boolean isCodeCorrect = confirmationCodeInput.equals("BANK-2026");

        boolean hasEnoughBalance = transferAmount <= accountBalance;
        boolean isLargeTransfer = transferAmount > 100000;
        boolean isNewAccount = accountAgeDays < 30;
        boolean hasTooManyFailedAttempts = failedLoginAttempts >=3;

        boolean basicConditionsPassed =
                isAccountActive && hasEnoughBalance && (!hasTooManyFailedAttempts);

        String transferType = isLargeTransfer ? "LARGE" : "STANDARD";

        String riskLevel;

        if (hasTooManyFailedAttempts) {
            riskLevel = "HIGH";
        } else if (isLargeTransfer || isNewAccount) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        if (isLargeTransfer) {
            System.out.println("SECURITY NOTICE: additional verification is required.");
        }

        boolean transferAllowed = false;
        // По умолчанию перевод запрещён.
        // Если никакое разрешающее условие не сработает,
        // значение так и останется false.

        if (basicConditionsPassed) {
            // Только если основные условия выполнены,
            // переходим к дополнительным проверкам.

            if (isLargeTransfer) {
                // Если перевод КРУПНЫЙ...

                if (!isNewAccount && isCodeCorrect) {
                    // Крупный перевод разрешается только если:
                    //
                    // 1. счёт НЕ новый
                    // 2. код подтверждения правильный
                    //
                    // Оба условия должны быть true.

                    transferAllowed = true;
                }

                // Если условие выше не выполнилось,
                // ничего не делаем.
                // transferAllowed уже был false.

            } else {
                // Если перевод НЕ крупный,
                // значит он обычный.

                if (isTrustedDevice || isCodeCorrect) {
                    // Обычный перевод разрешается,
                    // если выполняется ХОТЯ БЫ одно условие:
                    //
                    // 1. устройство доверенное
                    // ИЛИ
                    // 2. код правильный.

                    transferAllowed = true;
                }

                // Если оба условия false,
                // transferAllowed просто остаётся false.
            }
        }

        // Если basicConditionsPassed == false,
        // тело внешнего if вообще не выполнится.
        //
        // transferAllowed останется равным false,
        // поэтому отдельный else здесь не нужен.

        int balanceAfterTransfer = accountBalance;

        if (transferAllowed) {
            balanceAfterTransfer -= transferAmount;
        }

        String transferStatus = transferAllowed ? "APPROVED" : "DECLINED";

        System.out.println("Customer: "                 + customerName);
        System.out.println("Transfer type: "            + transferType);
        System.out.println("Risk level: "               + riskLevel);
        System.out.println("Account active: "           + isAccountActive);
        System.out.println("Trusted device: "           + isTrustedDevice);
        System.out.println("Code correct: "             + isCodeCorrect);
        System.out.println("Enough balance: "           + hasEnoughBalance);
        System.out.println("New account: "              + isNewAccount);
        System.out.println("Too many failed attempts: " + hasTooManyFailedAttempts);
        System.out.println("Transfer status: "          + transferStatus);
        System.out.println("Balance before: "           + accountBalance);
        System.out.println("Transfer amount: "          + transferAmount);
        System.out.println("Balance after: "            + balanceAfterTransfer);

        console.close();
    }
}
