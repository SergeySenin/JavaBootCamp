package org.study.bootcamp.task_11.data_center_work.v1.domain.model;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@Accessors(fluent = true)
public final class Server {

    private double load;
    private final double maxLoad;
    private double energyConsumption;

    public Server(double maxLoad, double initialLoad, double initialEnergyUsage) {
        validateMaxLoad(maxLoad);
        validateLoad(initialLoad, maxLoad);
        validateEnergy(initialEnergyUsage);

        this.maxLoad = maxLoad;
        this.load = initialLoad;
        this.energyConsumption = initialEnergyUsage;
    }

    public Server(double maxLoad) {
        this(maxLoad, 0.0, 0.0);
    }

    public void setLoad(double newLoad) {
        validateLoad(newLoad, this.maxLoad);
        this.load = newLoad;
    }

    public void addLoad(double delta) {
        double target = this.load + delta;
        validateLoad(target, this.maxLoad);
        this.load = target;
    }

    public void setEnergyConsumption(double energy) {
        validateEnergy(energy);
        this.energyConsumption = energy;
    }

    public double availableCapacity() {
        return Math.max(0.0, maxLoad - load);
    }

    public boolean isIdle() {
        return load <= 0.0;
    }

    private static void validateMaxLoad(double maxLoad) {
        if (!Double.isFinite(maxLoad) || maxLoad <= 0.0) {
            throw new IllegalArgumentException("maxLoad: must be finite and > 0");
        }
    }

    private static void validateLoad(double load, double maxLoad) {
        if (!Double.isFinite(load) || load < 0.0 || load > maxLoad) {
            throw new IllegalArgumentException("load: must be finite and in range [0.." + maxLoad + "]");
        }
    }

    private static void validateEnergy(double energy) {
        if (!Double.isFinite(energy) || energy < 0.0) {
            throw new IllegalArgumentException("energyConsumption: must be finite and >= 0");
        }
    }
}
