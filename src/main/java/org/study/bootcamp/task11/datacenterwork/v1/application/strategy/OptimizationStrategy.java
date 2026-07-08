package org.study.bootcamp.task11.datacenterwork.v1.application.strategy;

import org.study.bootcamp.task11.datacenterwork.v1.domain.model.DataCenter;

public interface OptimizationStrategy {
    void optimize(DataCenter dataCenter);
}
