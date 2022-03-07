package ru.atm;

public interface Safe {
    void set(Integer count, Banknote banknote);

    Integer get(Banknote banknote);

}
