package org.study.bootcamp.task_27.csv_builder.v1.domain.contract;

import java.util.*;

@FunctionalInterface
public interface VectorJoiner<T> {
    T join(List<T> vector);
}
