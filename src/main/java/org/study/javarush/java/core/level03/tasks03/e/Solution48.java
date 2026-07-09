package org.study.javarush.java.core.level03.tasks03.e;

public class Solution48 {
    public static void main(String[] args) {
        int yourAge = 25;
        boolean hasJob = true;
        boolean hasCreditHistory = false;
        boolean hasGuarantor = true;

        boolean approvedByFirstRule = (yourAge > 21 && hasJob) || (hasCreditHistory && hasGuarantor);
        boolean approvedBySecondRule = (yourAge > 21) && (hasJob || hasCreditHistory) && hasGuarantor;

        System.out.println(approvedByFirstRule);
        System.out.println(approvedBySecondRule);
    }
}
