package org.study.bootcamp.mishustin.task19.error.handling.v1.infrastructure.remote;

public final class RemoteService {

    private RemoteService() {}

    public static String call(String param) {
        if ("fail".equals(param)) {
            throw new RuntimeException("Service unavailable!");
        }
        return "Response for " + param;
    }
}
