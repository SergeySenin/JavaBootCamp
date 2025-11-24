package org.study.bootcamp.task_5.reverse_array.v1.application.service;

import java.util.*;

public final class ArrayReverser {

    private ArrayReverser() {}

    public static void reverse(int[] array) {
        validateArray(array);

        for (int left = 0, right = array.length - 1;
             left < right;
             left++, right--) {
            swap(array, left, right);
        }
    }

    public static int[] reversedCopy(int[] sourceArray) {
        validateArray(sourceArray);
        int[] copy = Arrays.copyOf(sourceArray, sourceArray.length);
        reverse(copy);
        return copy;
    }

    private static void validateArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array: must not be null");
        }
    }

    private static void swap(int[] array, int left, int right) {
        int buffer = array[left];
        array[left] = array[right];
        array[right] = buffer;
    }
}
