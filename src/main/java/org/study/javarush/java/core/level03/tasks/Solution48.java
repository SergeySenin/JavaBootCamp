package org.study.javarush.java.core.level03.tasks;

public class Solution48 {
    public static void main(String[] args) {
        int yourAge = 25;
        boolean hasJob = true;
        boolean hasCreditHistory = false;
        boolean hasGuarantor = true;
        boolean b1 = (yourAge > 21 && hasJob) || (hasCreditHistory && hasGuarantor);
        boolean b2 = (yourAge > 21) && (hasJob || hasCreditHistory) && hasGuarantor;
        System.out.println(b1);
        System.out.println(b2);
    }
}
