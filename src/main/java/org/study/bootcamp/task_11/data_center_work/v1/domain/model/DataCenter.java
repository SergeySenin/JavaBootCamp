package org.study.bootcamp.task_11.data_center_work.v1.domain.model;

import lombok.ToString;

import java.util.*;

@ToString
public final class DataCenter {

    private final List<Server> servers;

    public DataCenter() {
        this.servers = new ArrayList<>();
    }

    public DataCenter(List<Server> initialServers) {
        if (initialServers == null) {
            throw new IllegalArgumentException("initialServers: must not be null");
        }
        this.servers = new ArrayList<>(initialServers.size());

        for (Server server : initialServers) {
            if (server == null) {
                throw new IllegalArgumentException("initialServers: must not contain null elements");
            }
            this.servers.add(server);
        }
    }

    public void addServer(Server server) {
        Objects.requireNonNull(server, "server: must not be null");
        servers.add(server);
    }

    public boolean removeServer(Server server) {
        Objects.requireNonNull(server, "server: must not be null");
        return servers.remove(server);
    }

    public void clear() {
        servers.clear();
    }

    public int serverCount() {
        return servers.size();
    }

    public List<Server> servers() {
        return List.copyOf(servers);
    }
}
