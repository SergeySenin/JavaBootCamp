package org.study.bootcamp.mishustin.task24.calculator.v1.domain.contract;

@FunctionalInterface
public interface Calculator<T> {
    T calculate(T left, T right);
}
