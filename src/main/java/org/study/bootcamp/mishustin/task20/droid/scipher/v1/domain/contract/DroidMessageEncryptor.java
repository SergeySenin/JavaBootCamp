package org.study.bootcamp.mishustin.task20.droid.scipher.v1.domain.contract;

@FunctionalInterface
public interface DroidMessageEncryptor {
    String encrypt(String message, int key);
}
