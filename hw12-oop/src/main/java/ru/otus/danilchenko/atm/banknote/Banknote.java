package ru.otus.danilchenko.atm.banknote;

import java.util.Objects;

public class Banknote implements IBanknote {
    private final INominal nominal;

    public Banknote(INominal nominal) {
        Objects.requireNonNull(nominal);
        this.nominal = nominal;
    }


    @Override
    public INominal getNominal() {
        return nominal;
    }
}
