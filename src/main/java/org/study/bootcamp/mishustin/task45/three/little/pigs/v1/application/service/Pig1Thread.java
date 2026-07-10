package org.study.bootcamp.mishustin.task45.three.little.pigs.v1.application.service;

public class Pig1Thread extends PigThread {

    public Pig1Thread(String pigName) {
        super(pigName, "соломы");
    }

    @Override
    protected long getBuildDelayMillis() {
        return 1200L;
    }
}
