package org.study.bootcamp.mishustin.task47.spacex.program.v1.api.cli;

import org.study.bootcamp.mishustin.task47.spacex.program.v1.application.service.RocketLaunchPlanner;
import org.study.bootcamp.mishustin.task47.spacex.program.v1.domain.model.RocketLaunch;

import java.util.List;

public class Demo {

    public static void main(String[] args) {
        List<RocketLaunch> launches = List.of(
                new RocketLaunch("Falcon 9 — миссия A", 500),
                new RocketLaunch("Falcon Heavy — миссия B", 2500),
                new RocketLaunch("Starship — миссия C", 1500)
        );

        RocketLaunchPlanner planner = new RocketLaunchPlanner();
        planner.planRocketLaunches(launches);
    }
}
