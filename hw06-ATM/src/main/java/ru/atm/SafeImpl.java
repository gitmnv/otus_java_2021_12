package ru.atm;

import java.util.*;

import static ru.atm.Banknote.*;

public class SafeImpl implements Safe {

    private HashMap<Banknote, Integer> banknoteSafe = new HashMap<>();
    private final List<Banknote> banknoteNominalList = new ArrayList<>();
    private HashMap<Banknote, Integer> bak = new HashMap<>();

    SafeImpl() {
        banknoteNominalList.addAll(Arrays.stream(Banknote.values()).toList());

        banknoteSafe.putAll(Map.of(   //загрузка стартового набора банкнот
                FIVE_HUNDRED, 2,
                ONE_HUNDRED, 0,
                TWO_HUNDRED, 0,
                ONE_THOUSAND, 0,
                TWO_THOUSAND, 0,
                FIVE_THOUSAND, 1));
    }

    @Override
    public void add(Banknote banknote) {
        Integer count = banknoteSafe.get(banknote) + 1;
        banknoteSafe.put(banknote, count);
    }

    @Override
    public List<Banknote> get(int outSumm) {
        List<Banknote> banknotes = new ArrayList<>();
        try {
            bak = createSafeBackup();
        }catch (Exception exception){
            exception.printStackTrace();
        }

        try {
            for (int i = 0; i < banknoteNominalList.size() && outSumm > 0; i++) {
                Banknote banknote = banknoteNominalList.get(i);
                Integer banknoteCountInSafe = banknoteSafe.get(banknote);
                if (banknoteCountInSafe > 0) {
                    List<Banknote> banknoteFromSafe = getBanknotesFromSafe(banknote, outSumm);
                    outSumm -= banknote.getValue() * banknoteFromSafe.size();
                    banknoteSafe.put(banknote, banknoteCountInSafe - banknoteFromSafe.size());
                    banknotes.addAll(banknoteFromSafe);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (outSumm != 0) { //если что то идет не так, то выдача дс отменяется.
            restoreSafeFromBackup(bak);
            banknotes = new ArrayList<>();
            throw new RuntimeException("Банкомат не может выдать требуемую сумму.");
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

    private List<Banknote> getBanknotesFromSafe(Banknote banknote, int count) {
        List<Banknote> banknotes = new ArrayList<>();

        int banknoteSafeCount = banknoteSafe.get(banknote);
        if (count >= banknote.getValue()) {
            do {
                banknotes.add(banknote);
                banknoteSafeCount--;
                count -= banknote.getValue();
            } while (banknoteSafeCount > 0 && count >= banknote.getValue());
        }
        banknoteSafe.put(banknote, banknoteSafeCount);

        return banknotes;
    }

    private HashMap<Banknote, Integer> createSafeBackup() {
        return new HashMap<>(Map.copyOf(banknoteSafe));
    }

    private void restoreSafeFromBackup(HashMap<Banknote, Integer> backup) {
        banknoteSafe = backup;
    }

    private void calculateMoney() {

    }
}
