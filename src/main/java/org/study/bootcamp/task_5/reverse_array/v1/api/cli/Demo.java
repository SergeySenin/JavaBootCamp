package org.study.bootcamp.task_5.reverse_array.v1.api.cli;

import org.study.bootcamp.task_5.reverse_array.v1.application.service.ArrayReverser;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 4, 5};
        System.out.println("before: " + Arrays.toString(nums));
        ArrayReverser.reverse(nums);
        System.out.println(" after: " + Arrays.toString(nums));

        int[] original = {10, 20, 30};
        int[] reversed = ArrayReverser.reversedCopy(original);
        System.out.println("original: " + Arrays.toString(original));
        System.out.println("reversed: " + Arrays.toString(reversed));
    }
}
