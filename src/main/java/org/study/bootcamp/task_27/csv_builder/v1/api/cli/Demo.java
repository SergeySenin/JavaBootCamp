package org.study.bootcamp.task_27.csv_builder.v1.api.cli;

import org.study.bootcamp.task_27.csv_builder.v1.application.service.CsvBuilder;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<List<String>> table = List.of(
                List.of("1", "2", "3", "4", "5"),
                List.of("6", "7", "8", "9", "10"),
                List.of("11", "12", "13", "14", "15"),
                List.of("16", "17", "18", "19", "20"),
                List.of("21", "22", "23", "24", "25")
        );

        String csv = CsvBuilder.toCsv(table);
        System.out.println(csv);
    }
}
