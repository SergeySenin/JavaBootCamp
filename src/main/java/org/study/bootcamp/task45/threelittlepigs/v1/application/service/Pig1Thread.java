package org.study.bootcamp.task45.threelittlepigs.v1.application.service;

public class Pig1Thread extends PigThread {

    public Pig1Thread(String pigName) {
        super(pigName, "соломы");
    }

    @Override
    protected long getBuildDelayMillis() {
        return 1200L;
    }
}
