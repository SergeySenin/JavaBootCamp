package org.study.bootcamp.task28.matrixtransform.v1.application.contract;

import org.study.bootcamp.task28.matrixtransform.v1.domain.model.Coordinates;

@FunctionalInterface
public interface MatrixTransformer {
    Coordinates transform(
            int rowIndex,
            int colIndex
    );
}
