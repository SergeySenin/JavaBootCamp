package org.study.bootcamp.task_19.error_handling.v1.application.service;

import org.study.bootcamp.task_19.error_handling.v1.domain.contract.ExceptionHandler;

import java.util.*;
import java.util.function.*;

public final class ErrorHandler {

    private ErrorHandler() {}

    public static <T> T withErrorHandling(
            Supplier<T> actionSupplier,
            ExceptionHandler<T> exceptionHandler
    ) {
        Objects.requireNonNull(actionSupplier, "actionSupplier: must not be null");
        Objects.requireNonNull(exceptionHandler, "exceptionHandler: must not be null");

        try {
            return actionSupplier.get();
        } catch (Exception exception) {
            return exceptionHandler.handle(exception);
        }
    }
}
