package org.study.bootcamp.task_32.stream_basics_3.v1.domain.model;

import java.util.*;

public record PersonPair(String first, String second) {

    public PersonPair {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Names: must not be null");
        }
        if (first.equals(second)) {
            throw new IllegalArgumentException("Pair: must contain two distinct people");
        }
        if (first.compareTo(second) > 0) {
            String temp = first;
            first = second;
            second = temp;
        }
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PersonPair other)) return false;
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
