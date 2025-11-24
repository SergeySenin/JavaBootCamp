package org.study.bootcamp.task_27.csv_builder.v1.domain.contract;

import java.util.*;

@FunctionalInterface
public interface MatrixJoiner<T> {
    T join(List<List<T>> matrix);
}
