package org.study.javarush.java.core.level02.tasks02.g;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        String passengerNameInput = console.nextLine();
        String emailInput = console.nextLine();
        String departureCityInput = console.nextLine();
        String arrivalCityInput = console.nextLine();
        String ticketPriceText = console.nextLine();
        String travelMinutesText = console.nextLine();

        int availableSeats = console.nextInt();
        int soldTickets = console.nextInt();
        double routeDistance = console.nextDouble();

        byte carriageNumber = 7;

        String passengerName = passengerNameInput.trim();
        int passengerNameLength = passengerName.length();

        String email = emailInput.trim();
        email = email.toLowerCase();

        String departureCity = departureCityInput.trim();
        String arrivalCity = arrivalCityInput.trim();

        String routeName = departureCity + " - " + arrivalCity;

        String routeDisplayName = routeName;
        routeDisplayName = routeDisplayName.toUpperCase();

        int ticketPrice = Integer.parseInt(ticketPriceText);
        int travelMinutes = Integer.parseInt(travelMinutesText);

        int travelHours, remainingMinutes;
        travelHours = travelMinutes / 60;
        remainingMinutes = travelMinutes % 60;

        soldTickets++;
        availableSeats--;

        String ticketPriceAsText = String.valueOf(ticketPrice);

        int ticketNumber = 1000;
        ticketNumber++;

        String prefix = "";
        String ticketCode = prefix + "TRAIN-" + ticketNumber;

        String ticketFilePath = "C:\\Tickets\\" + ticketCode + ".txt";

        String electronicTicket =
                "\"RAILWAY TICKET\""                                                                      + "\n"
                        + "\tCode: "            + ticketCode                                              + "\n"
                        + "\tPassenger: "       + passengerName                                           + "\n"
                        + "\tEmail: "           + email                                                   + "\n"
                        + "\tName length: "     + passengerNameLength                                     + "\n"
                        + "\tRoute: "           + routeName                                               + "\n"
                        + "\tRoute display: "   + routeDisplayName                                        + "\n"
                        + "\tCarriage: "        + carriageNumber                                          + "\n"
                        + "\tDistance: "        + routeDistance       + " km"                             + "\n"
                        + "\tTravel time: "     + travelHours         + " h " + remainingMinutes + " min" + "\n"
                        + "\tPrice: "           + ticketPriceAsText                                       + "\n"
                        + "\tSold tickets: "    + soldTickets                                             + "\n"
                        + "\tAvailable seats: " + availableSeats                                          + "\n"
                        + "\tFile: "            + ticketFilePath;

        System.out.println(electronicTicket);

        console.close();
    }
}
