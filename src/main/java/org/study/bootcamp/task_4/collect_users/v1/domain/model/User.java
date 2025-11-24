package org.study.bootcamp.task_4.collect_users.v1.domain.model;

import org.study.bootcamp.task_4.collect_users.v1.application.validation.UserValidator;

import java.util.*;

public record User(Long id, String name, int age, Set<String> activities) {

    public User {
        UserValidator.validate(id, name, age, activities);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof User that)) return false;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Map<User, String> findHobbyLovers(List<User> users, Set<String> targetActivities) {

        Map<User, String> userToFirstMatchedActivity = new LinkedHashMap<>();
        if (isNullOrEmpty(users) || isNullOrEmpty(targetActivities)) {
            return userToFirstMatchedActivity;
        }

        Set<String> prioritizedActivities = stabilizeOrder(targetActivities);

        for (User currentUser : users) {
            if (currentUser == null) {
                continue;
            }

            Set<String> userActivities = currentUser.activities();
            if (isNullOrEmpty(userActivities)) {
                continue;
            }

            for (String currentTargetActivity : prioritizedActivities) {
                boolean hasMatch = userActivities.contains(currentTargetActivity);
                if (hasMatch) {
                    userToFirstMatchedActivity.put(currentUser, currentTargetActivity);
                    break;
                }
            }
        }

        return userToFirstMatchedActivity;
    }

    private static boolean isNullOrEmpty(Collection<?> collection) {
        boolean isNull = (collection == null);
        boolean isEmpty = !isNull && collection.isEmpty();
        return isNull || isEmpty;
    }

    private static Set<String> stabilizeOrder(Set<String> targetActivities) {
        return (targetActivities instanceof LinkedHashSet<?>)
                ? targetActivities
                : new LinkedHashSet<>(targetActivities);
    }

}
