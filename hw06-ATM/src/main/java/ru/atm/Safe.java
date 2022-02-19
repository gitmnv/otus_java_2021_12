package ru.atm;

import java.util.List;

public interface Safe {
    void add(Banknote banknote);

    List<Banknote> get(int count);

    int getBalance();
}
