package org.study.bootcamp.task_42.king_tournament.v1.api.cli;

import org.study.bootcamp.task_42.king_tournament.v1.application.service.King;
import org.study.bootcamp.task_42.king_tournament.v1.domain.model.Knight;
import org.study.bootcamp.task_42.king_tournament.v1.domain.model.Trial;

public class Demo {
    public static void main(String[] args) {

        Knight firstKnight = new Knight("Сер Лорас");
        firstKnight.addTrial(new Trial(firstKnight.getName(), "джостинг"));
        firstKnight.addTrial(new Trial(firstKnight.getName(), "бой на мечах"));

        Knight secondKnight = new Knight("Сер Джейме");
        secondKnight.addTrial(new Trial(secondKnight.getName(), "полоса препятствий"));
        secondKnight.addTrial(new Trial(secondKnight.getName(), "стрельба из лука"));

        King king = new King();
        king.addKnight(firstKnight);
        king.addKnight(secondKnight);

        king.startTournament();
    }
}
