package org.study.bootcamp.task_24.calculator.v1.domain.contract;

@FunctionalInterface
public interface Calculator<T> {
    T calculate(T left, T right);
}
