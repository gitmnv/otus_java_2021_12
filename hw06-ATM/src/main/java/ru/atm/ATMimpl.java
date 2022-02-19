package ru.atm;

import java.util.List;

public class ATMimpl implements ATM {
    private final Core atmCore = new ATMcore();

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
        if (count % 100 == 0) {
            return atmCore.out(count);
        } else {
            throw new RuntimeException("Сумма должна быть кратна 100");
        }
    }

    @Override
    public int balance() {
        return atmCore.balance();
    }
}
