package ru.atm;

import java.util.*;

import static ru.atm.Banknote.*;

public class SafeImpl implements Safe {

    private HashMap<Banknote, Integer> banknoteSafe = new HashMap<>();
    private final List<Banknote> banknoteNominalList = new ArrayList<>();

    SafeImpl() {
        banknoteNominalList.addAll(Arrays.stream(Banknote.values()).toList());

        banknoteSafe.putAll(Map.of(   //загрузка стартового набора банкнот
                FIVE_HUNDRED, 100,
                ONE_HUNDRED, 100,
                TWO_HUNDRED, 100,
                ONE_THOUSAND, 100,
                TWO_THOUSAND, 100,
                FIVE_THOUSAND, 1));
    }

    @Override
    public void add(Banknote banknote) {
        Integer count = banknoteSafe.get(banknote) + 1;
        banknoteSafe.put(banknote, count);
    }

    @Override
    public List<Banknote> get(int count) {
        List<Banknote> banknotes = new ArrayList<>();
        HashMap<Banknote, Integer> bak = banknoteSafe;

        try {
            for (int i = 0; i < banknoteNominalList.size() && count > 0; i++) {
                Banknote banknote = banknoteNominalList.get(i);
                int banknoteSafeCount = banknoteSafe.get(banknote);
                if (count >= banknote.getValue()) {
                    do {
                        banknotes.add(banknote);
                        banknoteSafeCount--;
                        count -= banknote.getValue();
                    } while (count >= banknote.getValue() && banknoteSafeCount > 0);
                }
                banknoteSafe.put(banknote, banknoteSafeCount);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (count != 0) { //если что то идет не так, то выдача дс отменяется.
            banknoteSafe = bak;
            banknotes = new ArrayList<>();
            throw new RuntimeException("Банкомат не работает");
        } else {
            return banknotes;
        }
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (Banknote banknote : banknoteNominalList) {
            balance += banknoteSafe.get(banknote) * banknote.getValue();
        }
        return balance;
    }
}
