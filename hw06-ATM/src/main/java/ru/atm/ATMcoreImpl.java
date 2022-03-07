package ru.atm;

import java.util.*;

public class ATMcoreImpl implements AtmCore {

    private final Safe moneySafe = new SafeImpl();
    private final List<Banknote> banknoteNominalList = new ArrayList<>();

    ATMcoreImpl() {
        banknoteNominalList.addAll(Arrays.stream(Banknote.values()).toList());
    }

    @Override
    public void in(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            moneySafe.set(moneySafe.get(banknote) + 1, banknote);
        }
    }

    @Override
    public List<Banknote> out(int outCashCount) {
        List<Banknote> outCash = new ArrayList<>();
        System.out.println(balance());

        for (int i = 0; i < banknoteNominalList.size(); i++) {
            Banknote banknote = banknoteNominalList.get(i);
            if (outCashCount >= banknote.getValue()) {
                Integer countBanknotesInSafe = moneySafe.get(banknote);
                if (countBanknotesInSafe > 0) {
                    do {
                        outCash.add(banknote);
                        countBanknotesInSafe--;
                        writeOffBanknote(banknote);
                        outCashCount -= banknote.getValue();
                    } while (outCashCount >= banknote.getValue() || countBanknotesInSafe > 0);

                }
            }
        }
        //outCashCount++;
        if (outCashCount != 0) { //если что то идет не так, то выдача дс отменяется.
            in(outCash);
            outCash = new ArrayList<>();
            throw new RuntimeException("Банкомат не может выдать требуемую сумму.");
        } else {
            return outCash;
        }
    }

    @Override
    public int balance() {
        int balance = 0;
        for (Banknote banknote : banknoteNominalList) {
            balance += moneySafe.get(banknote) * banknote.getValue();
        }
        return balance;
    }

    private void writeOffBanknote(Banknote banknote) {
        moneySafe.set(moneySafe.get(banknote) - 1, banknote);
    }
}
