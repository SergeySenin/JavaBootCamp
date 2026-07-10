package org.study.bootcamp.mishustin.task48.spotify.lock.v1.api.cli;

import org.study.bootcamp.mishustin.task48.spotify.lock.v1.application.service.MusicSession;
import org.study.bootcamp.mishustin.task48.spotify.lock.v1.domain.model.Player;

public class Demo {

    public static void main(String[] args) {
        Player player = new Player(10);
        MusicSession session = new MusicSession(player);

        session.runDemo();

        System.out.println("Сессия завершена.");
    }
}
