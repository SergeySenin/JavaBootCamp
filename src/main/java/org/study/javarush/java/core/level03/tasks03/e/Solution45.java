package org.study.javarush.java.core.level03.tasks03.e;

public class Solution45 {
    public static void main(String[] args) {
        boolean toTheSea = true;
        boolean hasPlaneTickets = false;
        boolean hasHotelRooms = true;

        boolean vacationHappened = toTheSea || hasPlaneTickets && hasHotelRooms;

        System.out.println(vacationHappened);
    }
}
