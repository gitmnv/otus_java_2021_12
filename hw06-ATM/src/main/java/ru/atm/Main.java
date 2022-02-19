package ru.atm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATMimpl();
        List<Banknote> clientMoney = new ArrayList<>();
        clientMoney.addAll(List.of(Banknote.FIVE_HUNDRED, Banknote.FIVE_HUNDRED));

        System.out.println(atm.balance());
        atm.withdrawal(clientMoney);
        System.out.println(atm.balance());
        System.out.println(atm.out(19900));
        System.out.println(atm.balance());
    }
}
