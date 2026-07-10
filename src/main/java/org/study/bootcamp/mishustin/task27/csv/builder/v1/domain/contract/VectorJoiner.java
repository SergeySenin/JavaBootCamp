package org.study.bootcamp.mishustin.task27.csv.builder.v1.domain.contract;

import java.util.*;

@FunctionalInterface
public interface VectorJoiner<T> {
    T join(List<T> vector);
}
