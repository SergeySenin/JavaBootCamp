package org.study.bootcamp.task48.spotifylock.v1.domain.model;

public class Player {

    private final Object lock = new Object();

    private boolean isPlaying;
    private int currentTrackNumber;
    private final int totalTracks;

    public Player(int totalTracks) {
        if (totalTracks <= 0) {
            throw new IllegalArgumentException("Количество треков должно быть больше 0");
        }

        this.totalTracks = totalTracks;
        this.currentTrackNumber = 1;
        this.isPlaying = false;
    }

    public void play() {
        synchronized (lock) {
            String userName = Thread.currentThread().getName();

            if (isPlaying) {
                System.out.printf("%s нажал play, но уже играет (трек %d)%n", userName, currentTrackNumber);
                return;
            }

            isPlaying = true;
            System.out.printf("%s нажал play → воспроизведение начато (трек %d)%n", userName, currentTrackNumber);
        }
    }

    public void pause() {
        synchronized (lock) {
            String userName = Thread.currentThread().getName();

            if (!isPlaying) {
                System.out.printf("%s нажал pause, но уже пауза (трек %d)%n", userName, currentTrackNumber);
                return;
            }

            isPlaying = false;
            System.out.printf("%s нажал pause → воспроизведение остановлено (трек %d)%n", userName, currentTrackNumber);
        }
    }

    public void skip() {
        synchronized (lock) {
            String userName = Thread.currentThread().getName();

            if (currentTrackNumber < totalTracks) {
                currentTrackNumber++;
            } else {
                currentTrackNumber = 1;
            }

            System.out.printf(
                    "%s нажал skip → текущий трек %d (%s)%n",
                    userName,
                    currentTrackNumber,
                    isPlaying ? "играет" : "пауза"
            );
        }
    }

    public void previous() {
        synchronized (lock) {
            String userName = Thread.currentThread().getName();

            if (currentTrackNumber > 1) {
                currentTrackNumber--;
            } else {
                currentTrackNumber = totalTracks;
            }

            System.out.printf(
                    "%s нажал previous → текущий трек %d (%s)%n",
                    userName,
                    currentTrackNumber,
                    isPlaying ? "играет" : "пауза"
            );
        }
    }
}
