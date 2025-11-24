package org.study.bootcamp.task_35.environment_monitoring.v1.application.analytics;

import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpact;
import org.study.bootcamp.task_35.environment_monitoring.v1.domain.model.EnvironmentalImpactType;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class ImpactAggregator {

    private ImpactAggregator() {}

    public static Map<Long, Double> totalEmissionsByCompany(
            LocalDate startInclusive,
            LocalDate endInclusive,
            List<EnvironmentalImpact> impacts,
            EnvironmentalImpactType impactType
    ) {
        requireValidPeriod(startInclusive, endInclusive);
        Objects.requireNonNull(impacts, "impacts");
        Objects.requireNonNull(impactType, "impactType");

        return impacts.stream()
                .filter(Objects::nonNull)
                .filter(impact -> impact.type() == impactType)
                .filter(impact -> !impact.date().isBefore(startInclusive) && !impact.date().isAfter(endInclusive))
                .collect(Collectors.groupingBy(
                        EnvironmentalImpact::companyId,
                        LinkedHashMap::new,
                        Collectors.summingDouble(EnvironmentalImpact::volume)
                ));
    }

    public static Map<YearMonth, Double> monthlyEmissionsForCompany(
            LocalDate startInclusive,
            LocalDate endInclusive,
            List<EnvironmentalImpact> impacts,
            long companyId,
            EnvironmentalImpactType impactType
    ) {
        requireValidPeriod(startInclusive, endInclusive);
        Objects.requireNonNull(impacts, "impacts");
        Objects.requireNonNull(impactType, "impactType");

        LinkedHashMap<YearMonth, Double> monthSeries = emptyMonthSeries(startInclusive, endInclusive);

        Map<YearMonth, Double> actualMonthlyTotals = impacts.stream()
                .filter(Objects::nonNull)
                .filter(impact -> impact.type() == impactType && impact.companyId() == companyId)
                .filter(impact -> !impact.date().isBefore(startInclusive) && !impact.date().isAfter(endInclusive))
                .collect(Collectors.groupingBy(
                        impact -> YearMonth.from(impact.date()),
                        Collectors.summingDouble(EnvironmentalImpact::volume)
                ));

        monthSeries.putAll(actualMonthlyTotals);
        return monthSeries;
    }

    private static LinkedHashMap<YearMonth, Double> emptyMonthSeries(LocalDate start, LocalDate end) {
        YearMonth startMonth = YearMonth.from(start.withDayOfMonth(1));
        YearMonth endMonth   = YearMonth.from(end.withDayOfMonth(1));

        return Stream.iterate(startMonth, month -> !month.isAfter(endMonth), month -> month.plusMonths(1))
                .collect(Collectors.toMap(
                        Function.identity(),
                        month -> 0.0,
                        (existingValue, newValue) -> existingValue,
                        LinkedHashMap::new
                ));
    }

    private static void requireValidPeriod(LocalDate startInclusive, LocalDate endInclusive) {
        Objects.requireNonNull(startInclusive, "startInclusive");
        Objects.requireNonNull(endInclusive, "endInclusive");
        if (endInclusive.isBefore(startInclusive)) {
            throw new IllegalArgumentException("endInclusive: must be >= startInclusive");
        }
    }
}
