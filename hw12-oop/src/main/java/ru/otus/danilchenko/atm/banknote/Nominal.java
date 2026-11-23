package ru.otus.danilchenko.atm.banknote;

public class Nominal implements INominal {
    private int nominalValue;

    public Nominal(int nominalValue) {
        if (nominalValue <= 0) {
            throw new IllegalArgumentException(String.format("Unsupported nominal value: %s", nominalValue));
        }
        this.nominalValue = nominalValue;
    }

    @Override
    public int getNominalValue() {
        return nominalValue;
    }
}
