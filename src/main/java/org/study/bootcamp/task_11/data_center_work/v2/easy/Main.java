package org.study.bootcamp.task_11.data_center_work.v2.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    static class Server {
        double load;
        double maxLoad;
        double energyConsumption;

        public Server(double maxLoad, double energyConsumption) {
            this.maxLoad = maxLoad;
            this.energyConsumption = energyConsumption;
            this.load = 0;
        }

        public boolean canHandle(double additionalLoad) {
            return load + additionalLoad <= maxLoad;
        }

        public void addLoad(double additionalLoad) {
            load += additionalLoad;
        }

        public void removeLoad(double loadToRemove) {
            load = Math.max(0, load - loadToRemove);
        }

        @Override
        public String toString() {
            return String.format("Сервер[нагрузка: %.1f/%.1f, энергия: %.1f]",
                    load, maxLoad, energyConsumption);
        }
    }

    static class DataCenter {
        List<Server> servers = new ArrayList<>();

        public void addServer(Server server) {
            servers.add(server);
        }

        public void removeServer(Server server) {
            servers.remove(server);
        }
    }

    static class ResourceRequest {
        double load;

        public ResourceRequest(double load) {
            this.load = load;
        }
    }

    interface OptimizationStrategy {
        void optimize(DataCenter dataCenter);
    }

    static class LoadBalancingOptimizationStrategy implements OptimizationStrategy {
        @Override
        public void optimize(DataCenter dataCenter) {
            System.out.println("⚖️ Оптимизация балансировки нагрузки...");

            double totalLoad = dataCenter.servers.stream().mapToDouble(s -> s.load).sum();
            double targetLoadPerServer = totalLoad / dataCenter.servers.size();

            for (Server server : dataCenter.servers) {
                if (server.load > targetLoadPerServer) {
                    double excess = server.load - targetLoadPerServer;
                    redistributeLoad(excess, server, dataCenter.servers);
                }
            }
        }

        private void redistributeLoad(double load, Server fromServer, List<Server> servers) {
            for (Server toServer : servers) {
                if (toServer != fromServer && toServer.canHandle(load)) {
                    fromServer.removeLoad(load);
                    toServer.addLoad(load);
                    System.out.println("📊 Перемещено " + load + " с " + fromServer + " на " + toServer);
                    break;
                }
            }
        }
    }

    static class EnergyEfficiencyOptimizationStrategy implements OptimizationStrategy {
        @Override
        public void optimize(DataCenter dataCenter) {
            System.out.println("💡 Оптимизация энергоэффективности...");

            List<Server> servers = new ArrayList<>(dataCenter.servers);
            servers.sort((s1, s2) -> Double.compare(s2.load/s2.energyConsumption, s1.load/s1.energyConsumption));

            double totalLoad = servers.stream().mapToDouble(s -> s.load).sum();

            servers.forEach(s -> s.load = 0);

            for (Server server : servers) {
                if (totalLoad <= 0) break;

                double loadToAssign = Math.min(totalLoad, server.maxLoad);
                server.addLoad(loadToAssign);
                totalLoad -= loadToAssign;

                System.out.println("🔋 Назначено " + loadToAssign + " на " + server);
            }

            if (totalLoad > 0) {
                for (Server server : servers) {
                    if (totalLoad <= 0) break;
                    if (server.load < server.maxLoad) {
                        double available = server.maxLoad - server.load;
                        double loadToAssign = Math.min(totalLoad, available);
                        server.addLoad(loadToAssign);
                        totalLoad -= loadToAssign;
                    }
                }
            }
        }
    }

    static class DataCenterService {
        private OptimizationStrategy currentStrategy;
        private Timer optimizationTimer;

        public DataCenterService(OptimizationStrategy strategy) {
            this.currentStrategy = strategy;
            startScheduledOptimization();
        }

        public void setOptimizationStrategy(OptimizationStrategy strategy) {
            this.currentStrategy = strategy;
        }

        public void addServer(DataCenter dataCenter, Server server) {
            dataCenter.addServer(server);
            System.out.println("➕ Добавлен " + server);
        }

        public void removeServer(DataCenter dataCenter, Server server) {
            dataCenter.removeServer(server);
            System.out.println("➖ Удалён " + server);
        }

        public double getTotalEnergyConsumption(DataCenter dataCenter) {
            double total = dataCenter.servers.stream().mapToDouble(s -> s.energyConsumption).sum();
            System.out.println("⚡ Общее энергопотребление: " + total);
            return total;
        }

        public boolean allocateResources(DataCenter dataCenter, ResourceRequest request) {
            System.out.println("📦 Запрос ресурсов: " + request.load);

            for (Server server : dataCenter.servers) {
                if (server.canHandle(request.load)) {
                    server.addLoad(request.load);
                    System.out.println("✅ Ресурсы выделены на " + server);
                    return true;
                }
            }

            double remainingLoad = request.load;
            for (Server server : dataCenter.servers) {
                if (remainingLoad <= 0) break;

                double available = server.maxLoad - server.load;
                double loadToAssign = Math.min(remainingLoad, available);

                if (loadToAssign > 0) {
                    server.addLoad(loadToAssign);
                    remainingLoad -= loadToAssign;
                    System.out.println("✅ Часть нагрузки " + loadToAssign + " на " + server);
                }
            }

            boolean success = remainingLoad <= 0;
            System.out.println(success ? "✅ Все ресурсы выделены" : "❌ Недостаточно ресурсов");
            return success;
        }

        public void releaseResources(DataCenter dataCenter, ResourceRequest request) {
            System.out.println("🔄 Освобождение ресурсов: " + request.load);

            double remainingLoad = request.load;
            for (Server server : dataCenter.servers) {
                if (remainingLoad <= 0) break;

                double loadToRemove = Math.min(remainingLoad, server.load);
                server.removeLoad(loadToRemove);
                remainingLoad -= loadToRemove;

                if (loadToRemove > 0) {
                    System.out.println("🔄 Освобождено " + loadToRemove + " с " + server);
                }
            }
        }

        private void startScheduledOptimization() {
            optimizationTimer = new Timer();
            optimizationTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\n⏰ Автоматическая оптимизация...");
                }
            }, 0, 30 * 60 * 1000);
        }

        public void manualOptimize(DataCenter dataCenter) {
            if (currentStrategy != null) {
                currentStrategy.optimize(dataCenter);
            }
        }

        public void stopOptimization() {
            if (optimizationTimer != null) {
                optimizationTimer.cancel();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DataCenter dataCenter = new DataCenter();
        DataCenterService service = new DataCenterService(new LoadBalancingOptimizationStrategy());

        Server server1 = new Server(100, 50);
        Server server2 = new Server(150, 80);
        Server server3 = new Server(200, 100);

        service.addServer(dataCenter, server1);
        service.addServer(dataCenter, server2);
        service.addServer(dataCenter, server3);

        System.out.println("\n=== ТЕСТ РАСПРЕДЕЛЕНИЯ НАГРУЗКИ ===");
        service.allocateResources(dataCenter, new ResourceRequest(120));
        service.allocateResources(dataCenter, new ResourceRequest(80));

        System.out.println("\n=== ТЕСТ БАЛАНСИРОВКИ НАГРУЗКИ ===");
        service.manualOptimize(dataCenter);

        System.out.println("\n=== ТЕСТ ЭНЕРГОЭФФЕКТИВНОСТИ ===");
        service.setOptimizationStrategy(new EnergyEfficiencyOptimizationStrategy());
        service.manualOptimize(dataCenter);

        System.out.println("\n=== ТЕСТ ОСВОБОЖДЕНИЯ РЕСУРСОВ ===");
        service.releaseResources(dataCenter, new ResourceRequest(100));

        service.getTotalEnergyConsumption(dataCenter);

        service.stopOptimization();
    }
}
