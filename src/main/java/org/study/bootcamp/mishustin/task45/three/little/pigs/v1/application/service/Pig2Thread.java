package org.study.bootcamp.mishustin.task45.three.little.pigs.v1.application.service;

public class Pig2Thread extends PigThread {

    public Pig2Thread(String pigName) {
        super(pigName, "веток");
    }

    @Override
    protected long getBuildDelayMillis() {
        return 1800L;
    }
}
