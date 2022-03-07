package ru.atm;

import java.util.*;

import static ru.atm.Banknote.*;

public class SafeImpl implements Safe {

    private HashMap<Banknote, Integer> banknoteSafe = new HashMap<>();

    SafeImpl() {
        banknoteSafe.putAll(Map.of(   //загрузка стартового набора банкнот
                FIVE_HUNDRED, 2,
                ONE_HUNDRED, 0,
                TWO_HUNDRED, 0,
                ONE_THOUSAND, 0,
                TWO_THOUSAND, 0,
                FIVE_THOUSAND, 1));
    }

    @Override
    public void set(Integer count, Banknote banknote) {
        banknoteSafe.put(banknote, count);
    }

    @Override
    public Integer get(Banknote banknote) {
        return banknoteSafe.get(banknote);
    }
}
