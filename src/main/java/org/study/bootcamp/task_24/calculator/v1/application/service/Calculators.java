package org.study.bootcamp.task_24.calculator.v1.application.service;

import org.study.bootcamp.task_24.calculator.v1.domain.contract.Calculator;

import java.util.*;

public final class Calculators {

    private Calculators() {
    }

    public static int calculate(List<Integer> numbers, Calculator<Integer> calculator) {
        requireNonNull(numbers, "numbers");
        requireNonNull(calculator, "calculator");

        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("numbers: must not be empty");
        }
        if (numbers.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("numbers: must not contain null elements");
        }

        int result = numbers.get(0);
        for (int index = 1; index < numbers.size(); index++) {
            result = calculator.calculate(result, numbers.get(index));
        }
        return result;
    }

    public static int sum(List<Integer> numbers) {
        return calculate(numbers, (left, right) -> left + right);
    }

    public static int product(List<Integer> numbers) {
        return calculate(numbers, (left, right) -> left * right);
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + ": must not be null");
        }
        return value;
    }
}
