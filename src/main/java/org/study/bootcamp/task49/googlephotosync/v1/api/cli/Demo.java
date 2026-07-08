package org.study.bootcamp.task49.googlephotosync.v1.api.cli;

import org.study.bootcamp.task49.googlephotosync.v1.application.service.GooglePhotosAutoUploader;

public class Demo {

    public static void main(String[] args) {
        GooglePhotosAutoUploader uploader = new GooglePhotosAutoUploader();

        Thread autoUploadThread = new Thread(uploader::startAutoUpload, "автозагрузка");
        Thread photoProducerThread = new Thread(() -> addPhotos(uploader), "добавление-фото");

        autoUploadThread.start();
        photoProducerThread.start();

        try {
            photoProducerThread.join();
            autoUploadThread.interrupt();
            autoUploadThread.join();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            autoUploadThread.interrupt();
            photoProducerThread.interrupt();
            throw new IllegalStateException("Ожидание завершения программы прервано", interruptedException);
        }

        System.out.println("Синхронизация Google Photos завершена.");
    }

    private static void addPhotos(GooglePhotosAutoUploader uploader) {
        for (int photoNumber = 1; photoNumber <= 8; photoNumber++) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            String photoPath = "C:\\фото\\снимок-" + photoNumber + ".jpg";
            uploader.onNewPhotoAdded(photoPath);

            try {
                Thread.sleep(450L);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
