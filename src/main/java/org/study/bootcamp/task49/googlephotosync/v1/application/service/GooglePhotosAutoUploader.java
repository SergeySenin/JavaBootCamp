package org.study.bootcamp.task49.googlephotosync.v1.application.service;

import java.util.ArrayList;
import java.util.List;

public class GooglePhotosAutoUploader {

    private final Object lock = new Object();
    private final List<String> photosToUpload = new ArrayList<>();

    public void startAutoUpload() {
        while (!Thread.currentThread().isInterrupted()) {
            List<String> photosBatch = waitForPhotosAndDrainBatch();
            if (photosBatch == null) {
                return; // прервано во время wait()
            }
            uploadPhotos(photosBatch);
        }
    }

    public void onNewPhotoAdded(String photoPath) {
        if (photoPath == null || photoPath.isBlank()) {
            throw new IllegalArgumentException("Путь к фотографии не должен быть пустым");
        }

        synchronized (lock) {
            photosToUpload.add(photoPath);
            lock.notifyAll();
        }
    }

    private List<String> waitForPhotosAndDrainBatch() {
        synchronized (lock) {
            while (photosToUpload.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            List<String> photosBatch = new ArrayList<>(photosToUpload);
            photosToUpload.clear();
            return photosBatch;
        }
    }

    private static final long UPLOAD_DELAY_MILLIS = 300L;

    private void uploadPhotos(List<String> photosBatch) {
        String threadName = Thread.currentThread().getName();

        for (int index = 0; index < photosBatch.size(); index++) {
            if (Thread.currentThread().isInterrupted()) {
                requeueRemaining(photosBatch, index);
                System.out.printf(
                        "[%s] Загрузка прервана, оставшиеся фото возвращены в очередь%n",
                        threadName
                );
                return;
            }

            String photoPath = photosBatch.get(index);
            System.out.printf(
                    "[%s] Загружаю фото: %s%n",
                    threadName,
                    photoPath
            );

            try {
                Thread.sleep(UPLOAD_DELAY_MILLIS); // имитация загрузки, это не busy-wait
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                requeueRemaining(photosBatch, index);
                System.out.printf(
                        "[%s] Загрузка прервана во время работы, оставшиеся фото возвращены в очередь%n",
                        threadName
                );
                return;
            }

            System.out.printf(
                    "[%s] Фото загружено: %s%n",
                    threadName,
                    photoPath
            );
        }
    }

    private void requeueRemaining(List<String> photosBatch, int startIndex) {
        synchronized (lock) {
            for (int index = startIndex; index < photosBatch.size(); index++) {
                photosToUpload.add(photosBatch.get(index));
            }
            lock.notifyAll();
        }
    }
}
