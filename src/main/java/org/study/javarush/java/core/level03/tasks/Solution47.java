package org.study.javarush.java.core.level03.tasks;

public class Solution47 {
    public static void main(String[] args) {
        boolean hasInvitation = true;
        boolean dressCodeMet = false;
        boolean passwordIsCorrect = true;
        boolean admitted = hasInvitation && dressCodeMet && passwordIsCorrect;
        boolean admitted0 = (hasInvitation && dressCodeMet) && passwordIsCorrect;
        boolean admitted1 = hasInvitation && (dressCodeMet && passwordIsCorrect);
        System.out.println(admitted);
        System.out.println(admitted0);
        System.out.println(admitted1);
    }
}
