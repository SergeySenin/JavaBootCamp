package org.study.bootcamp.task_19.error_handling.v1.infrastructure.remote;

public final class RemoteService {

    private RemoteService() {}

    public static String call(String param) {
        if ("fail".equals(param)) {
            throw new RuntimeException("Service unavailable!");
        }
        return "Response for " + param;
    }
}
