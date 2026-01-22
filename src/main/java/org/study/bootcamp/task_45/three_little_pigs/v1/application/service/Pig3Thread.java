package org.study.bootcamp.task_45.three_little_pigs.v1.application.service;

public class Pig3Thread extends PigThread {

    public Pig3Thread(String pigName) {
        super(pigName, "камня");
    }

    @Override
    protected long getBuildDelayMillis() {
        return 2400L;
    }
}
