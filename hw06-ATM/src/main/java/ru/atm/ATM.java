package ru.atm;

import java.util.List;

public interface ATM {
    void withdrawal(List<Banknote> banknotes);

    List<Banknote> out(int count);

    int balance();
}
