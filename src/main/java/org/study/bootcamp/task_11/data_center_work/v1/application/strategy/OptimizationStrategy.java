package org.study.bootcamp.task_11.data_center_work.v1.application.strategy;

import org.study.bootcamp.task_11.data_center_work.v1.domain.model.DataCenter;

public interface OptimizationStrategy {
    void optimize(DataCenter dataCenter);
}
