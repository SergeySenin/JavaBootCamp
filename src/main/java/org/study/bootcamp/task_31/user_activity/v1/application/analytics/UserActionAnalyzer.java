package org.study.bootcamp.task_31.user_activity.v1.application.analytics;

import org.study.bootcamp.task_31.user_activity.v1.domain.model.ActionType;
import org.study.bootcamp.task_31.user_activity.v1.domain.model.UserAction;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import java.time.*;

public final class UserActionAnalyzer {

    private UserActionAnalyzer() {}

    public static List<String> topActiveUsers(List<UserAction> actions, int limit) {
        requireNonNull(actions, "actions");
        requirePositive(limit, "limit");

        Map<String, Long> actionCountByUser = actions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(UserAction::userName, Collectors.counting()));

        return actionCountByUser.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry::getKey)
                )
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static List<String> topPopularHashtags(List<UserAction> actions, int limit) {
        requireNonNull(actions, "actions");
        requirePositive(limit, "limit");

        Pattern hashtagPattern = Pattern.compile("#[\\p{Alnum}_]+");

        Map<String, Long> hashtagToCount = actions.stream()
                .filter(Objects::nonNull)
                .filter(userAction ->
                        userAction.actionType() == ActionType.POST || userAction.actionType() == ActionType.COMMENT)
                .map(UserAction::content)
                .filter(Objects::nonNull)
                .flatMap(text -> {
                    Matcher matcher = hashtagPattern.matcher(text);
                    List<String> found = new ArrayList<>();
                    while (matcher.find()) {
                        found.add(matcher.group().toLowerCase(Locale.ROOT));
                    }
                    return found.stream();
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return hashtagToCount.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry::getKey)
                )
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static List<String> topCommentersLastMonth(List<UserAction> actions, int limit) {
        LocalDate sinceInclusive = LocalDate.now().minusMonths(1);
        LocalDate untilInclusive = LocalDate.now();
        return topCommentersInRange(actions, limit, sinceInclusive, untilInclusive);
    }

    public static List<String> topCommentersInRange(List<UserAction> actions,
                                                    int limit,
                                                    LocalDate sinceInclusive,
                                                    LocalDate untilInclusive) {
        requireNonNull(actions, "actions");
        requirePositive(limit, "limit");
        requireNonNull(sinceInclusive, "sinceInclusive");
        requireNonNull(untilInclusive, "untilInclusive");

        Map<String, Long> commentsCountByUser = actions.stream()
                .filter(Objects::nonNull)
                .filter(userAction -> userAction.actionType() == ActionType.COMMENT)
                .filter(userAction -> {
                    LocalDate date = userAction.actionDate();
                    return (date.isEqual(sinceInclusive) || date.isAfter(sinceInclusive)) &&
                            (date.isEqual(untilInclusive) || date.isBefore(untilInclusive));
                })
                .collect(Collectors.groupingBy(UserAction::userName, Collectors.counting()));

        return commentsCountByUser.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry::getKey)
                )
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static Map<String, Double> actionTypePercentages(List<UserAction> actions) {
        requireNonNull(actions, "actions");
        if (actions.isEmpty()) return Map.of();

        long totalCount = actions.size();

        Map<ActionType, Long> countByType = actions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(UserAction::actionType, Collectors.counting()));

        Map<String, Double> percentagesByType = new LinkedHashMap<>();
        for (ActionType type : ActionType.values()) {
            long count = countByType.getOrDefault(type, 0L);
            double percentage = (count * 100.0) / totalCount;
            percentagesByType.put(type.name(), percentage);
        }
        return percentagesByType;
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(name + " must not be null");
        return value;
    }

    private static void requirePositive(int value, String name) {
        if (value <= 0) throw new IllegalArgumentException(name + " must be > 0");
    }
}
