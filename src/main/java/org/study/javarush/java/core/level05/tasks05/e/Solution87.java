package org.study.javarush.java.core.level05.tasks05.e;

import java.util.Date;

public class Solution87 {
    public static void main(String[] args) {

        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

        System.out.println(utilDate);
        System.out.println(sqlDate);
    }
}
