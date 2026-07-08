package org.study.bootcamp.task27.csvbuilder.v1.domain.contract;

import java.util.*;

@FunctionalInterface
public interface MatrixJoiner<T> {
    T join(List<List<T>> matrix);
}
