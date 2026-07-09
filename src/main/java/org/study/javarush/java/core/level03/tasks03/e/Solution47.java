package org.study.javarush.java.core.level03.tasks03.e;

public class Solution47 {
    public static void main(String[] args) {
        boolean hasInvitation = true;
        boolean dressCodeMet = false;
        boolean passwordIsCorrect = true;

        boolean admitted = hasInvitation && dressCodeMet && passwordIsCorrect;
        boolean admittedLeftGrouped = (hasInvitation && dressCodeMet) && passwordIsCorrect;
        boolean admittedRightGrouped = hasInvitation && (dressCodeMet && passwordIsCorrect);

        System.out.println(admitted);
        System.out.println(admittedLeftGrouped);
        System.out.println(admittedRightGrouped);
    }
}
