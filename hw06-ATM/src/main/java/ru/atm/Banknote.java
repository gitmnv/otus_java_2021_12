package ru.atm;

public enum Banknote {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100);

    private static final int minimumDenominationOfBanknotes = ONE_HUNDRED.getValue();

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int getMinimumDenominationOfBanknotes() {
        return minimumDenominationOfBanknotes;
    }
}
