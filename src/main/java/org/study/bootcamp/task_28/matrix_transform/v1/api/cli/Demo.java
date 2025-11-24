package org.study.bootcamp.task_28.matrix_transform.v1.api.cli;

import org.study.bootcamp.task_28.matrix_transform.v1.domain.model.Coordinates;
import org.study.bootcamp.task_28.matrix_transform.v1.domain.model.FlipDirection;
import org.study.bootcamp.task_28.matrix_transform.v1.application.service.MatrixTransforms;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        int[][] original = {
                {1, 2},
                {3, 4}
        };

        System.out.println("Original:");
        printMatrix(original);

        int[][] flippedH = MatrixTransforms.flipMatrix(original, FlipDirection.HORIZONTAL);
        System.out.println("\nHorizontal flip:");
        printMatrix(flippedH);

        int[][] flippedV = MatrixTransforms.flipMatrix(original, FlipDirection.VERTICAL);
        System.out.println("\nVertical flip:");
        printMatrix(flippedV);

        int[][] transposed = MatrixTransforms.transformMatrix(original,
                (rowIndex, colIndex) -> new Coordinates(colIndex, rowIndex));
        System.out.println("\nTransposed (custom transformer):");
        printMatrix(transposed);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
