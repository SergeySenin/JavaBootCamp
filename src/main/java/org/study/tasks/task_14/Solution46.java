package org.study.tasks.task_14;

public class Solution46 {
    public static void main(String[] args) {
        boolean ticketsOnSale = false;
        boolean haveMoneyForTicket = true;
        boolean concertNotCanceled  = false;
        boolean canBuy = ticketsOnSale || haveMoneyForTicket && concertNotCanceled;
        boolean canBuyWithNewMeaning = (ticketsOnSale || haveMoneyForTicket) && concertNotCanceled;
        System.out.println(canBuy);
        System.out.println(canBuyWithNewMeaning);
    }
}
