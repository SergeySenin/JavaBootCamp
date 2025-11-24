package org.study.bootcamp.task_35.environment_monitoring.v1.api.cli;

import org.study.bootcamp.task_35.environment_monitoring.v1.application.service.ImpactReportService;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.Company;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpact;
import org.study.bootcamp.task_35.environment_monitoring.v1.infrastructure.datasource.ImpactDataSource;

import java.time.LocalDate;
import java.util.*;

public class Demo {
    public static void main(String[] args) throws Exception {
        List<Company> companies = List.of(
                new Company(101, "PollutingCo", 11815),
                new Company(102, "SuperCompany", 14580),
                new Company(103, "GreenCompany",  9800),
                new Company(104, "EcoCompany",    7500),
                new Company(105, "MegaIndustrial", 12200)
        );

        ImpactReportService reports = new ImpactReportService(companies);
        ImpactDataSource rawSource = ImpactDataSource.classpathCsv("environmental_impact_100.csv");

        List<EnvironmentalImpact> loaded = rawSource.load();
        LocalDate pivotDate = loaded.stream()
                .map(EnvironmentalImpact::date)
                .max(Comparator.naturalOrder())
                .orElse(LocalDate.now());

        ImpactDataSource cachedSource = () -> loaded;

        reports.printCompanyMonthlyEmissions(cachedSource, 101, pivotDate);
        reports.printTop3EmittersLastYear(cachedSource, pivotDate);
        reports.printPerEmployeeLastYear(cachedSource, pivotDate);
    }
}
