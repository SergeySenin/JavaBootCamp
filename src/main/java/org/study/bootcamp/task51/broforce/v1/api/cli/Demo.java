package org.study.bootcamp.task51.broforce.v1.api.cli;

import org.study.bootcamp.task51.broforce.v1.application.service.GameRunner;
import org.study.bootcamp.task51.broforce.v1.domain.model.Game;

public class Demo {

    public static void main(String[] args) {
        Game game = new Game(10);

        GameRunner gameRunner = new GameRunner(game);
        gameRunner.runGame(4, 200, 80);

        System.out.printf(
                "Итог: очки=%d, потеряно жизней=%d%n",
                game.getScore(),
                game.getLostLives()
        );
    }
}
