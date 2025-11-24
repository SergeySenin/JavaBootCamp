package org.study.bootcamp.task_15.meta_universe.v1.api.cli;

import org.study.bootcamp.task_15.meta_universe.v1.application.service.NotificationManager;
import org.study.bootcamp.task_15.meta_universe.v1.domain.model.Notification;
import org.study.bootcamp.task_15.meta_universe.v1.domain.model.NotificationType;

public class Demo {
    public static void main(String[] args) {

        NotificationManager notificationManager = new NotificationManager();

        notificationManager.registerHandler(
                NotificationType.EMAIL,
                notification -> System.out.println("Email: " + notification.message())
        );
        notificationManager.registerHandler(
                NotificationType.SMS,
                notification -> System.out.println("SMS: " + notification.message())
        );
        notificationManager.registerHandler(
                NotificationType.PUSH,
                notification -> System.out.println("Push: " + notification.message())
        );

        notificationManager.addFilter(notification ->
                !notification.message().toLowerCase().contains("forbidden")
        );
        notificationManager.addTransformer(notification ->
                notification.withMessage(notification.message() + " — © Meta")
        );

        notificationManager.sendNotification(
                new Notification(NotificationType.EMAIL, "Your account has been activated!")
        );
        notificationManager.sendNotification(
                new Notification(NotificationType.SMS, "Your password has been changed!")
        );
        notificationManager.sendNotification(
                new Notification(NotificationType.PUSH, "You have got mail!")
        );
        notificationManager.sendNotification(
                new Notification(NotificationType.PUSH, "Forbidden content!")
        );
    }
}
