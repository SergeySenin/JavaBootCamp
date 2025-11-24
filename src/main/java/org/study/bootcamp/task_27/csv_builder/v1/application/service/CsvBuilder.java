package org.study.bootcamp.task_27.csv_builder.v1.application.service;

import org.study.bootcamp.task_27.csv_builder.v1.domain.contract.MatrixJoiner;
import org.study.bootcamp.task_27.csv_builder.v1.domain.contract.VectorJoiner;

import java.util.*;

public final class CsvBuilder {

    private CsvBuilder() {}

    public static String toCsv(List<List<String>> table) {
        requireNonNull(table, "table");
        if (table.isEmpty()) {
            throw new IllegalArgumentException("table: must not be empty");
        }

        VectorJoiner<String> vectorJoiner = (List<String> row) -> {
            requireNonNull(row, "row");
            if (row.isEmpty()) {
                throw new IllegalArgumentException("row: must not be empty");
            }

            StringBuilder line = new StringBuilder();
            line.append(requireNonNull(row.get(0), "cell[0]"));

            for (int i = 1; i < row.size(); i++) {
                String cell = requireNonNull(row.get(i), "cell[" + i + "]");
                line.append(", ").append(cell);
            }
            return line.toString();
        };

        MatrixJoiner<String> matrixJoiner = (List<List<String>> matrix) -> {
            requireNonNull(matrix, "matrix");
            if (matrix.isEmpty()) {
                throw new IllegalArgumentException("matrix: must not be empty");
            }

            StringBuilder csv = new StringBuilder();
            for (int rowIndex = 0; rowIndex < matrix.size(); rowIndex++) {
                List<String> row = matrix.get(rowIndex);
                String line = vectorJoiner.join(row);
                if (rowIndex > 0) csv.append('\n');
                csv.append(line);
            }
            return csv.toString();
        };

        return matrixJoiner.join(table);
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + ": must not be null");
        }
        return value;
    }
}
