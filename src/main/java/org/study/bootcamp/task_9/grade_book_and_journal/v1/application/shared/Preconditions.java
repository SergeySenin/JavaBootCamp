package org.study.bootcamp.task_9.grade_book_and_journal.v1.application.shared;

import java.util.*;

public final class Preconditions {

    private Preconditions() {}

    public static <T> T requireNonNull(T value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException(paramName + ": must not be null");
        }
        return value;
    }

    public static <K, V> Map<K, V> requireNonNullMap(Map<K, V> map, String paramName) {
        if (map == null) {
            throw new IllegalArgumentException(paramName + ": must not be null");
        }
        return map;
    }

    public static <T> List<T> requireNonNullList(List<T> list, String paramName) {
        if (list == null) {
            throw new IllegalArgumentException(paramName + ": must not be null");
        }
        return list;
    }
}
