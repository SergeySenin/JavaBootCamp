package org.study.bootcamp.task_11.data_center_work.v1.application.strategy;

import org.study.bootcamp.task_11.data_center_work.v1.domain.model.DataCenter;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.Server;

import java.util.*;

// TODO[validation]: Можно добавить проверку, чтобы нагрузка не превышала maxLoad.
public final class LoadBalancingOptimizationStrategy implements OptimizationStrategy {

    @Override
    public void optimize(DataCenter dataCenter) {
        Objects.requireNonNull(dataCenter, "dataCenter: must not be null");

        List<Server> servers = dataCenter.servers();
        if (servers.isEmpty()) {
            return;
        }

        double totalLoad = servers.stream()
                .mapToDouble(Server::load)
                .sum();

        double averageLoad = totalLoad / servers.size();

        for (Server server : servers) {
            server.setLoad(averageLoad);
        }
    }
}
