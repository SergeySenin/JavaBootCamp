package org.study.bootcamp.task_11.data_center_work.v1.application.strategy;

import org.study.bootcamp.task_11.data_center_work.v1.domain.model.DataCenter;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.Server;

import java.util.*;

public final class EnergyEfficiencyOptimizationStrategy implements OptimizationStrategy {

    @Override
    public void optimize(DataCenter dataCenter) {
        Objects.requireNonNull(dataCenter, "dataCenter: must not be null");

        List<Server> serversCopy = new ArrayList<>(dataCenter.servers());
        if (serversCopy.isEmpty()) {
            return;
        }

        double totalLoad = serversCopy.stream()
                .mapToDouble(Server::load)
                .sum();

        serversCopy.sort(Comparator.comparingDouble(Server::maxLoad).reversed());

        double remainingLoad = totalLoad;
        for (Server server : serversCopy) {
            if (remainingLoad <= 0) {
                server.setLoad(0.0);
                server.setEnergyConsumption(0.0);
                continue;
            }

            double loadForThisServer = Math.min(server.maxLoad(), remainingLoad);
            server.setLoad(loadForThisServer);
            server.setEnergyConsumption(loadForThisServer * 0.1);

            remainingLoad -= loadForThisServer;
        }
    }
}
