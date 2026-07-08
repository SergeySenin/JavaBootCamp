package org.study.bootcamp.task35.environmentmonitoring.v1.infrastructure.datasource;

import org.study.bootcamp.task35.environmentmonitoring.v1.domain.model.EnvironmentalImpact;
import org.study.bootcamp.task35.environmentmonitoring.v1.infrastructure.csv.ImpactCsvLoader;

import java.io.IOException;
import java.util.*;

@FunctionalInterface
public interface ImpactDataSource {

    List<EnvironmentalImpact> load() throws IOException;

    static ImpactDataSource classpathCsv(String resourceName) {
        return () -> ImpactCsvLoader.loadFromClasspath(resourceName);
    }

    static ImpactDataSource fileCsv(String filePath) {
        return () -> ImpactCsvLoader.loadFromFilePath(filePath);
    }

    static ImpactDataSource of(List<EnvironmentalImpact> impacts) {
        return () -> impacts;
    }
}
