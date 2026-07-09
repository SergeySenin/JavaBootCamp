package org.study.javarush.java.core.level02.tasks02.c;

public class Solution20 {
    public static void main(String[] args) {
        String cityName = "  Istanbul  ";

        System.out.println(cityName.length());

        String trimmedCityName = cityName.trim();

        System.out.println(trimmedCityName.length());
        System.out.println(trimmedCityName.toUpperCase());
        System.out.println(trimmedCityName.toLowerCase());
    }
}
