package org.study.bootcamp.task_26.triangle_area.v1.application.service;

import java.util.function.*;

public class TriangleAreaCalculator {

    private static final Function<Double, Function<Double, Double>> ADD       = x -> y -> x + y;
    private static final Function<Double, Function<Double, Double>> MULTIPLY  = x -> y -> x * y;
    private static final Function<Double, Function<Double, Double>> SUBTRACT  = x -> y -> x - y;
    private static final Function<Double, Function<Double, Double>> DIVIDE    = x -> y -> x / y;
    private static final Function<Double, Double>                   SQUARE_ROOT = x -> Math.sqrt(x);

    public static Double calculateTriangleArea(double sideA, double sideB, double sideC) {
        validateSides(sideA, sideB, sideC);
        validateTriangleInequality(sideA, sideB, sideC);

        double perimeter = ADD.apply(ADD.apply(sideA).apply(sideB)).apply(sideC);
        double semiPerimeter = DIVIDE.apply(perimeter).apply(2.0);

        double pMinusA = SUBTRACT.apply(semiPerimeter).apply(sideA);
        double pMinusB = SUBTRACT.apply(semiPerimeter).apply(sideB);
        double pMinusC = SUBTRACT.apply(semiPerimeter).apply(sideC);

        double heronProduct =
                MULTIPLY.apply(semiPerimeter).apply(
                        MULTIPLY.apply(pMinusA).apply(
                                MULTIPLY.apply(pMinusB).apply(pMinusC)
                        )
                );

        return SQUARE_ROOT.apply(heronProduct);
    }

    private static void validateSides(double a, double b, double c) {
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c)) {
            throw new IllegalArgumentException("sides: must be finite numbers");
        }
        if (a <= 0.0 || b <= 0.0 || c <= 0.0) {
            throw new IllegalArgumentException("sides: must be > 0");
        }
    }

    private static void validateTriangleInequality(double a, double b, double c) {
        boolean violates = ADD.apply(a).apply(b) <= c || ADD.apply(a).apply(c) <= b || ADD.apply(b).apply(c) <= a;

        if (violates) {
            throw new IllegalArgumentException("triangle inequality violated");
        }
    }
}
