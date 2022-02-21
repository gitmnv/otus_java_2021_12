package ru.atm;

import java.util.List;

public class ATMcoreImpl implements AtmCore {

    private final Safe moneySafe = new SafeImpl();

    @Override
    public void in(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            moneySafe.add(banknote);
        }
    }

    @Override
    public List<Banknote> out(int count) {
        return moneySafe.get(count);
    }

    @Override
    public int balance() {
        return moneySafe.getBalance();
    }


}
