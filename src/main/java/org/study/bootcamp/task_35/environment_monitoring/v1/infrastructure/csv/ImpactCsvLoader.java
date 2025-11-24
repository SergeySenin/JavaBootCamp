package org.study.bootcamp.task_35.environment_monitoring.v1.infrastructure.csv;

import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpact;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpactType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public final class ImpactCsvLoader {

    private static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private ImpactCsvLoader() {}

    public static List<EnvironmentalImpact> loadFromClasspath(String resourceName) throws IOException {
        Objects.requireNonNull(resourceName, "resourceName: must not be null");
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
            if (stream == null) {
                throw new FileNotFoundException("Resource not found on classpath: " + resourceName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                return parseCsv(reader.lines());
            }
        }
    }

    public static List<EnvironmentalImpact> loadFromFilePath(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "filePath: must not be null");
        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            return parseCsv(lines);
        }
    }

    private static List<EnvironmentalImpact> parseCsv(Stream<String> lines) {
        return lines
                .skip(1)
                .filter(line -> line != null && !line.isBlank())
                .map(line -> line.split(",", -1))
                .filter(columns -> columns.length >= 5)
                .map(columns -> {
                    try {
                        long id = Long.parseLong(columns[0].trim());
                        long companyId = Long.parseLong(columns[1].trim());
                        double volume = Double.parseDouble(columns[2].trim());
                        LocalDate date = LocalDate.parse(columns[3].trim(), CSV_DATE);
                        EnvironmentalImpactType type =
                                EnvironmentalImpactType.valueOf(columns[4].trim().toUpperCase(Locale.ROOT));
                        return new EnvironmentalImpact(id, companyId, volume, date, type);
                    } catch (Exception exception) {
                        System.err.println(
                                "Skip bad line: " + Arrays.toString(columns) + " (" + exception.getMessage() + ")"
                        );
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
