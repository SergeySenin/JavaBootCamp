package org.study.bootcamp.task_20.droids_cipher.v1.domain.model;

import org.study.bootcamp.task_20.droids_cipher.v1.domain.contract.DroidMessageEncryptor;

import java.util.*;

public record Droid(String name) {

    private static final DroidMessageEncryptor CAESAR = (inputText, rawKey) -> {
        int normalizedShift = normalizeShift(rawKey);

        StringBuilder result = new StringBuilder(inputText.length());

        for (char character : inputText.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int offset = (character - base + normalizedShift) % 26;
                result.append((char) (base + offset));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    };

    public void sendMessage(Droid recipient, String plainText, int key) {
        Objects.requireNonNull(recipient, "recipient: must not be null");

        String validatedText = requireNonBlank(plainText, "plainText");
        String cipherText = encryptMessage(validatedText, key);

        System.out.println(this.name + " sent encrypted message: " + cipherText);
        recipient.receiveMessage(cipherText, key);
    }

    public void receiveMessage(String cipherText, int key) {
        String validatedText = requireNonBlank(cipherText, "cipherText");
        String decryptedText = decryptMessage(validatedText, key);

        System.out.println(this.name + " received decrypted message: " + decryptedText);
    }

    public String encryptMessage(String plainText, int key) {
        Objects.requireNonNull(plainText, "plainText: must not be null");

        return CAESAR.encrypt(plainText, key);
    }

    public String decryptMessage(String cipherText, int key) {
        Objects.requireNonNull(cipherText, "cipherText: must not be null");

        return CAESAR.encrypt(cipherText, -key);
    }

    private static int normalizeShift(int key) {
        int mod = key % 26;
        return (mod + 26) % 26;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + ": must not be null/blank");
        }
        return value;
    }
}
