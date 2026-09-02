package org.study.javarush.java.core.level04.tasks04.g;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        String missionNameInput = console.nextLine();
        String missionName = missionNameInput.trim();

        int batteryLevel;

        do {
            batteryLevel = console.nextInt();

            if (batteryLevel < 20 || batteryLevel > 100) {
                System.out.println("Invalid battery level.");
            }
        } while (batteryLevel < 20 || batteryLevel > 100);

        System.out.println("Battery accepted: " + batteryLevel + "%");

        int checkpointCount = console.nextInt();
        int targetCheckpoint = console.nextInt();
        int mapSize = console.nextInt();
        int beaconRow = console.nextInt();
        int beaconColumn = console.nextInt();

        for (int countdown = 3; countdown >= 1; countdown--) {
            System.out.println(countdown);
        }

        System.out.println("LAUNCH");

        int sensorReading = console.nextInt();
        int sensorReadingCount = 0;
        int sensorReadingSum = 0;
        int dangerousObjectCount = 0;

        while (sensorReading >= 0) {
            sensorReadingCount++;
            sensorReadingSum += sensorReading;

            if (sensorReading <= 100) {
                dangerousObjectCount++;
            }

            sensorReading = console.nextInt();
        }

        System.out.println("Sensor readings: "   + sensorReadingCount);
        System.out.println("Sensor sum: "        + sensorReadingSum);
        System.out.println("Dangerous objects: " + dangerousObjectCount);

        boolean targetFound = false;

        for (int checkpoint = 1; checkpoint <= checkpointCount; checkpoint++) {
            if (checkpoint % 4 == 0) {
                System.out.println("Checkpoint " + checkpoint + ": RESTRICTED");
                continue;
            }

            System.out.println("Checkpoint " + checkpoint + ": scanning");

            if (checkpoint == targetCheckpoint) {
                targetFound = true;
                System.out.println("TARGET FOUND AT CHECKPOINT " + checkpoint);
                break;
            }
        }

        String searchStatus = targetFound ? "FOUND" : "NOT FOUND";

        System.out.println("Search status: " + searchStatus);

        for (int row = 0; row < mapSize; row++) {
            for (int column = 0; column < mapSize; column++) {

                if (row == beaconRow && column == beaconColumn) {
                    System.out.print("B");
                } else {
                    System.out.print(".");
                }
            }

            System.out.println();
        }

        System.out.println("Mission: "           + missionName);
        System.out.println("Battery: "           + batteryLevel           + "%");
        System.out.println("Sensor readings: "   + sensorReadingCount);
        System.out.println("Dangerous objects: " + dangerousObjectCount);
        System.out.println("Target checkpoint: " + targetCheckpoint);
        System.out.println("Search status: "     + searchStatus);
        System.out.println("Map size: "          + mapSize                + "x"   + mapSize);

        console.close();
    }
}
