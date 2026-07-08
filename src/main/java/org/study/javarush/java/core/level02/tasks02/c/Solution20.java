package org.study.javarush.java.core.level02.tasks02.c;

public class Solution20 {
    public static void main(String[] args) {
        String cityName = " Istanbul ";

        int cityNameLength = cityName.length();
        System.out.println(cityNameLength);

        String trimmedCityName = cityName.trim();

        int trimmedCityNameLength = trimmedCityName.length();
        System.out.println(trimmedCityNameLength);

        String upperCaseCityName = trimmedCityName.toUpperCase();
        System.out.println(upperCaseCityName);

        String lowerCaseCityName = trimmedCityName.toLowerCase();
        System.out.println(lowerCaseCityName);
    }
}
