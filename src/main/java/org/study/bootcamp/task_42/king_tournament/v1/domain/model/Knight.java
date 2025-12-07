package org.study.bootcamp.task_42.king_tournament.v1.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Knight {

    private final String name;
    private final List<Trial> trials = new ArrayList<>();

    public Knight(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("имя рыцаря не должно быть пустым");
        }
        this.name = name;
    }

    public void addTrial(Trial trial) {
        if (trial == null) {
            throw new IllegalArgumentException("испытание не должно быть null");
        }
        trials.add(trial);
    }

    public List<Future<?>> startTrials(ExecutorService executorService) {
        if (executorService == null) {
            throw new IllegalArgumentException("ExecutorService не должен быть null");
        }

        List<Future<?>> futures = new ArrayList<>();
        for (Trial trial : trials) {
            futures.add(executorService.submit(trial));
        }
        return futures;
    }

    public String getName() {
        return name;
    }
}
