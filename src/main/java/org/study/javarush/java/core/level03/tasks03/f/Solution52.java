package org.study.javarush.java.core.level03.tasks03.f;

public class Solution52 {
    public static void main(String[] args) {
        int visitorAge = 13;
        String ticketCategory = visitorAge < 7 ? "Детский" :
                visitorAge >= 7 && visitorAge <= 17 ? "Подростковый" :
                        visitorAge >= 18 && visitorAge <= 64 ? "Взрослый" :
                                "Пенсионный";
        System.out.println(ticketCategory);
    }
}
