package org.study.bootcamp.task_11.data_center_work.v1.api.cli;

import org.study.bootcamp.task_11.data_center_work.v1.domain.model.DataCenter;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.ResourceRequest;
import org.study.bootcamp.task_11.data_center_work.v1.domain.model.Server;
import org.study.bootcamp.task_11.data_center_work.v1.application.service.DataCenterService;
import org.study.bootcamp.task_11.data_center_work.v1.application.strategy.EnergyEfficiencyOptimizationStrategy;
import org.study.bootcamp.task_11.data_center_work.v1.application.strategy.LoadBalancingOptimizationStrategy;

public class Demo {
    public static void main(String[] args) {

        DataCenter dataCenter = new DataCenter();
        DataCenterService service = new DataCenterService();

        dataCenter.addServer(new Server(100, 0, 10));
        dataCenter.addServer(new Server(150, 0, 15));
        dataCenter.addServer(new Server(200, 0, 20));

        System.out.println("=== Initial state ===");
        System.out.println(dataCenter);

        ResourceRequest request = new ResourceRequest(180);
        boolean allocated = service.allocateResources(dataCenter, request);
        System.out.println("\nAllocate 180 units -> " + allocated);
        System.out.println(dataCenter);

        System.out.println("\n--- Load Balancing Strategy ---");
        service.optimize(dataCenter, new LoadBalancingOptimizationStrategy());
        System.out.println(dataCenter);

        service.releaseResources(dataCenter, new ResourceRequest(100));
        System.out.println("\nRelease 100 units");
        System.out.println(dataCenter);

        System.out.println("\n--- Energy Efficiency Strategy ---");
        service.optimize(dataCenter, new EnergyEfficiencyOptimizationStrategy());
        System.out.println(dataCenter);

        double totalEnergy = service.getTotalEnergyConsumption(dataCenter);
        System.out.println("\nTotal energy consumption: " + totalEnergy);
    }
}
