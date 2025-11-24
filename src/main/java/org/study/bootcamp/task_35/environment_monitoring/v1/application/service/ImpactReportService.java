package org.study.bootcamp.task_35.environment_monitoring.v1.application.service;

import org.study.bootcamp.task_35.environment_monitoring.v1.application.analytics.ImpactAggregator;
import org.study.bootcamp.task_35.environment_monitoring.v1.application.shared.DateRange;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.Company;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpact;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpactType;
import org.study.bootcamp.task_35.environment_monitoring.v1.infrastructure.datasource.ImpactDataSource;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public final class ImpactReportService {

    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("yyyy.MM");
    private static final DateTimeFormatter DATE_FMT  = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Map<Long, Company> companiesById;

    public ImpactReportService(List<Company> companies) {
        Objects.requireNonNull(companies, "companies");
        this.companiesById = companies.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Company::id,
                        company -> company,
                        (existingCompany, duplicateCompany) -> existingCompany,
                        LinkedHashMap::new
                ));
    }

    public void printCompanyMonthlyEmissions(ImpactDataSource source, long companyId, LocalDate today) throws Exception {
        List<EnvironmentalImpact> impacts = source.load();
        DateRange window = DateRange.last12MonthsUpTo(today);
        printCompanyMonthlyEmissions(impacts, companyId, window, today);
    }

    public void printTop3EmittersLastYear(ImpactDataSource source, LocalDate today) throws Exception {
        List<EnvironmentalImpact> impacts = source.load();
        DateRange window = DateRange.last12MonthsUpTo(today);
        printTop3EmittersLastYear(impacts, window);
    }

    public void printPerEmployeeLastYear(ImpactDataSource source, LocalDate today) throws Exception {
        List<EnvironmentalImpact> impacts = source.load();
        DateRange window = DateRange.last12MonthsUpTo(today);
        printPerEmployeeLastYear(impacts, window);
    }

    private void printCompanyMonthlyEmissions(
            List<EnvironmentalImpact> impacts, long companyId, DateRange window, LocalDate today
    ) {
        Objects.requireNonNull(impacts, "impacts");
        Objects.requireNonNull(window, "window");
        Objects.requireNonNull(today, "today");

        String companyName = resolveCompanyName(companyId);

        System.out.println("Company Name: " + companyName);
        System.out.println("Today's date: " + DATE_FMT.format(today));
        System.out.println("Month    GasEmission");

        Map<YearMonth, Double> monthlyTotals = ImpactAggregator.monthlyEmissionsForCompany(
                window.startInclusive(), window.endInclusive(),
                impacts, companyId, EnvironmentalImpactType.GAS_EMISSION
        );

        monthlyTotals.entrySet().stream()
                .sorted(Map.Entry.<YearMonth, Double>comparingByKey().reversed())
                .forEach(monthEntry -> System.out.printf(
                        Locale.ROOT, "%s  %.2f%n",
                        MONTH_FMT.format(monthEntry.getKey().atDay(1)),
                        monthEntry.getValue()
                ));

        double total = monthlyTotals.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf(Locale.ROOT, "Total    %.2f%n", total);
    }

    private void printTop3EmittersLastYear(List<EnvironmentalImpact> impacts, DateRange window) {
        Objects.requireNonNull(impacts, "impacts");
        Objects.requireNonNull(window, "window");

        Map<Long, Double> totalsByCompany = ImpactAggregator.totalEmissionsByCompany(
                window.startInclusive(), window.endInclusive(),
                impacts, EnvironmentalImpactType.GAS_EMISSION
        );

        List<Map.Entry<Long, Double>> top3Companies = totalsByCompany.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(3)
                .toList();

        System.out.println("\nCompany           TotalGasEmission  AvgGasEmission/Month  MinGasEmission/Month");

        for (Map.Entry<Long, Double> companyEntry : top3Companies) {
            long companyId = companyEntry.getKey();
            String companyName = resolveCompanyName(companyId);

            Map<YearMonth, Double> monthly = ImpactAggregator.monthlyEmissionsForCompany(
                    window.startInclusive(), window.endInclusive(),
                    impacts, companyId, EnvironmentalImpactType.GAS_EMISSION
            );

            double total = monthly.values().stream().mapToDouble(Double::doubleValue).sum();
            double averagePerMonth = total / 12.0;
            double minPerMonth = monthly.values().stream().mapToDouble(Double::doubleValue).min().orElse(0.0);

            System.out.printf(Locale.ROOT, "%-17s %-16.2f %-21.2f %.2f%n",
                    companyName, total, averagePerMonth, minPerMonth);
        }
    }

    private void printPerEmployeeLastYear(List<EnvironmentalImpact> impacts, DateRange window) {
        Objects.requireNonNull(impacts, "impacts");
        Objects.requireNonNull(window, "window");

        Map<Long, Double> totalsByCompany = ImpactAggregator.totalEmissionsByCompany(
                window.startInclusive(), window.endInclusive(),
                impacts, EnvironmentalImpactType.GAS_EMISSION
        );

        System.out.println("\nCompany            TotalGasEmission Employees GasEmissionPerEmployee");

        companiesById.values().stream()
                .sorted(Comparator.comparing(Company::companyName))
                .forEach(company -> {
                    double total = totalsByCompany.getOrDefault(company.id(), 0.0);
                    int employees = company.totalEmployees();
                    String perEmployee = (employees > 0)
                            ? String.format(Locale.ROOT, "%.2f", total / employees)
                            : "N/A";
                    System.out.printf(Locale.ROOT, "%-19s %-16.2f %-9d %s%n",
                            company.companyName(), total, employees, perEmployee);
                });
    }

    private String resolveCompanyName(long companyId) {
        Company company = companiesById.get(companyId);
        return (company != null) ? company.companyName() : ("Company#" + companyId);
    }
}
