package org.study.bootcamp.task_26.triangle_area.v1.api.cli;

import org.study.bootcamp.task_26.triangle_area.v1.application.service.TriangleAreaCalculator;

public class Demo {
    public static void main(String[] args) {

        double area = TriangleAreaCalculator.calculateTriangleArea(3, 4, 5);
        System.out.println("Triangle area: " + area);

        System.out.println("Triangle area (6,7,8): " +
                TriangleAreaCalculator.calculateTriangleArea(6, 7, 8));
    }
}
