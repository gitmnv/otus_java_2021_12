package ru.atm;

import java.util.List;

public interface AtmCore {
    void in(List<Banknote> banknotes);

    List<Banknote> out(int count);

    int balance();
}
