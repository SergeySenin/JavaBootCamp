package org.study.bootcamp.mishustin.task28.matrix.transform.v1.application.contract;

import org.study.bootcamp.mishustin.task28.matrix.transform.v1.domain.model.Coordinates;

@FunctionalInterface
public interface MatrixTransformer {
    Coordinates transform(
            int rowIndex,
            int colIndex
    );
}
