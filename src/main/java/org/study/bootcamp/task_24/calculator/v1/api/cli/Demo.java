package org.study.bootcamp.task_24.calculator.v1.api.cli;

import org.study.bootcamp.task_24.calculator.v1.application.service.Calculators;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<Integer> sample = List.of(2, 3, 5, 7);

        int sum = Calculators.sum(sample);
        int product = Calculators.product(sample);

        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);

        int max = Calculators.calculate(sample, (left, right) -> Math.max(left, right));
        System.out.println("Max: " + max);

        int diff = Calculators.calculate(sample, (left, right) -> left - right);
        System.out.println("Left-fold diff: " + diff);
    }
}
