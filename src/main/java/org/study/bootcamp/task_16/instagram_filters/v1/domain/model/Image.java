package org.study.bootcamp.task_16.instagram_filters.v1.domain.model;

import org.apache.commons.lang3.Validate;

public record Image(String name, String description) {

    public Image {
        Validate.notBlank(name, "name: must not be null/blank");
        Validate.notBlank(description, "description: must not be null/blank");
    }

    public Image withDescription(String newDescription) {
        return new Image(this.name, newDescription);
    }
}
