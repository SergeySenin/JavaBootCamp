package org.study.bootcamp.task_28.matrix_transform.v1.application.service;

import org.study.bootcamp.task_28.matrix_transform.v1.domain.model.Coordinates;
import org.study.bootcamp.task_28.matrix_transform.v1.domain.model.FlipDirection;
import org.study.bootcamp.task_28.matrix_transform.v1.application.contract.MatrixTransformer;

import java.util.*;

public final class MatrixTransforms {

    private MatrixTransforms() {}

    public static int[][] transformMatrix(int[][] matrix, MatrixTransformer transformer) {
        Objects.requireNonNull(matrix, "matrix must not be null");
        Objects.requireNonNull(transformer, "transformer must not be null");

        if (matrix.length == 0) {
            return new int[0][0];
        }
        ensureRectangular(matrix);

        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        int[][] transformed = new int[rowCount][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int colIndex = 0; colIndex < columnCount; colIndex++) {
                Coordinates target = transformer.transform(rowIndex, colIndex);
                validateTarget(target, rowCount, columnCount);
                transformed[target.coordinateX()][target.coordinateY()] = matrix[rowIndex][colIndex];
            }
        }
        return transformed;
    }

    public static int[][] flipMatrix(int[][] matrix, FlipDirection flipDirection) {
        Objects.requireNonNull(matrix, "matrix must not be null");
        Objects.requireNonNull(flipDirection, "flipDirection must not be null");

        if (matrix.length == 0) {
            return new int[0][0];
        }
        ensureRectangular(matrix);

        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        return switch (flipDirection) {
            case HORIZONTAL -> transformMatrix(
                    matrix,
                    (rowIndex, columnIndex) -> new Coordinates(rowIndex, columnCount - 1 - columnIndex)
            );
            case VERTICAL -> transformMatrix(
                    matrix,
                    (rowIndex, columnIndex) -> new Coordinates(rowCount - 1 - rowIndex, columnIndex)
            );
        };
    }

    private static void ensureRectangular(int[][] matrix) {
        int expected = matrix[0].length;
        if (expected == 0) {
            throw new IllegalArgumentException("matrix: must not have empty rows");
        }
        for (int i = 0; i < matrix.length; i++) {
            int[] row = Objects.requireNonNull(matrix[i], "matrix row[" + i + "] must not be null");
            if (row.length != expected) {
                throw new IllegalArgumentException("matrix must be rectangular: row[" + i + "] has different length");
            }
        }
    }

    private static void validateTarget(Coordinates target, int rowCount, int columnCount) {
        int x = target.coordinateX();
        int y = target.coordinateY();
        if (x < 0 || x >= rowCount || y < 0 || y >= columnCount) {
            throw new IllegalArgumentException("target coordinates out of bounds: (" + x + ", " + y + ")");
        }
    }
}
