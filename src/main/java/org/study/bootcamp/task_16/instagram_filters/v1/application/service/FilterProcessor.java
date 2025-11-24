package org.study.bootcamp.task_16.instagram_filters.v1.application.service;

import org.study.bootcamp.task_16.instagram_filters.v1.domain.model.Image;

import java.util.*;
import java.util.function.*;

public final class FilterProcessor {

    public Image applyFilter(
            Image image,
            Function<Image, Image> filter
    ) {
        Objects.requireNonNull(image, "image: must not be null");
        Objects.requireNonNull(filter, "filter: must not be null");
        return filter.apply(image);
    }

    public Function<Image, Image> combineFilters(
            Function<Image, Image> firstFilter,
            Function<Image, Image> secondFilter
    ) {
        Objects.requireNonNull(firstFilter, "first filter: must not be null");
        Objects.requireNonNull(secondFilter, "second filter: must not be null");
        return firstFilter.andThen(secondFilter);
    }
}
