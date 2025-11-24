package org.study.bootcamp.task_28.matrix_transform.v1.application.contract;

import org.study.bootcamp.task_28.matrix_transform.v1.domain.model.Coordinates;

@FunctionalInterface
public interface MatrixTransformer {
    Coordinates transform(
            int rowIndex,
            int colIndex
    );
}
