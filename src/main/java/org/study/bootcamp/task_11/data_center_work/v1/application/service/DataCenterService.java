package org.study.bootcamp.task_11.data_center_work.v1.application.service;

import org.study.bootcamp.task_11.data_center_work.v1.application.strategy.OptimizationStrategy;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.DataCenter;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.ResourceRequest;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.Server;

import java.util.*;

public final class DataCenterService {

    public void addServer(DataCenter dataCenter, Server server) {
        requireNonNull(dataCenter, "dataCenter");
        dataCenter.addServer(Objects.requireNonNull(server, "server"));
    }

    public boolean removeServer(DataCenter dataCenter, Server server) {
        requireNonNull(dataCenter, "dataCenter");
        return dataCenter.removeServer(Objects.requireNonNull(server, "server"));
    }

    public double getTotalEnergyConsumption(DataCenter dataCenter) {
        requireNonNull(dataCenter, "dataCenter");
        return dataCenter.servers().stream()
                .mapToDouble(Server::energyConsumption)
                .sum();
    }

    public boolean allocateResources(DataCenter dataCenter, ResourceRequest request) {
        requireNonNull(dataCenter, "dataCenter");
        requireNonNull(request, "request");

        double remainingLoad = request.load();

        for (Server server : dataCenter.servers()) {
            double freeCapacity = server.availableCapacity();
            if (freeCapacity > 0) {
                double toAllocate = Math.min(freeCapacity, remainingLoad);
                server.addLoad(toAllocate);
                remainingLoad -= toAllocate;
            }
            if (remainingLoad <= 0) {
                return true;
            }
        }
        return false;
    }

    public void releaseResources(DataCenter dataCenter, ResourceRequest request) {
        requireNonNull(dataCenter, "dataCenter");
        requireNonNull(request, "request");

        double toRelease = request.load();

        for (Server server : dataCenter.servers()) {
            double reduction = Math.min(server.load(), toRelease);
            server.setLoad(server.load() - reduction);
            toRelease -= reduction;
            if (toRelease <= 0) {
                break;
            }
        }
    }

    public void optimize(DataCenter dataCenter, OptimizationStrategy strategy) {
        requireNonNull(dataCenter, "dataCenter");
        if (strategy != null) {
            strategy.optimize(dataCenter);
        }
    }

    private static void requireNonNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name + ": must not be null");
        }
    }
}
