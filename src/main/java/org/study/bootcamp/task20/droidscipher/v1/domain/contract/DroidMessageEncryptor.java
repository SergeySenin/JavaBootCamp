package org.study.bootcamp.task20.droidscipher.v1.domain.contract;

@FunctionalInterface
public interface DroidMessageEncryptor {
    String encrypt(String message, int key);
}
