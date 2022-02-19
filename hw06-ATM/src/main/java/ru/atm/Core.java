package ru.atm;

import java.util.List;

public interface Core {
    void in(List<Banknote> banknotes);

    List<Banknote> out(int count);

    int balance();
}
