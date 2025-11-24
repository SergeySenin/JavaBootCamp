package org.study.bootcamp.task_19.error_handling.v1.api.cli;

import org.study.bootcamp.task_19.error_handling.v1.application.service.ErrorHandler;
import org.study.bootcamp.task_19.error_handling.v1.infrastructure.remote.RemoteService;

public class Demo {
    public static void main(String[] args) {

        String successfulResult = ErrorHandler.withErrorHandling(
                () -> RemoteService.call("success"),
                exception -> {
                    System.out.println("Error during call: " + exception.getMessage());
                    return "DEFAULT";
                }
        );
        System.out.println("Result: " + successfulResult);

        String fallbackResult = ErrorHandler.withErrorHandling(
                () -> RemoteService.call("fail"),
                exception -> {
                    System.out.println("Error during call: " + exception.getMessage());
                    return "DEFAULT";
                }
        );
        System.out.println("Result: " + fallbackResult);
    }
}
