package org.study.bootcamp.task_20.droids_cipher.v1.domain.contract;

@FunctionalInterface
public interface DroidMessageEncryptor {
    String encrypt(String message, int key);
}
