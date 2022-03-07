package ru.atm;

import java.util.List;

public class ATMimpl implements ATM {
    private final AtmCore atmCore = new ATMcoreImpl();

    @Override
    public void withdrawal(List<Banknote> banknotes) {
        if (banknotes != null) {
            atmCore.in(banknotes);
        } else {
            throw new RuntimeException("Необходимо внести наличные");
        }
    }

    @Override
    public List<Banknote> out(int count) {
        int minimumDenominationOfBanknotes = Banknote.getMinimumDenominationOfBanknotes();

        if (count % minimumDenominationOfBanknotes == 0) {
            return atmCore.out(count);
        } else {
            throw new RuntimeException("Сумма должна быть кратна " + minimumDenominationOfBanknotes);
        }
    }

    @Override
    public int balance() {
        return atmCore.balance();
    }
}
