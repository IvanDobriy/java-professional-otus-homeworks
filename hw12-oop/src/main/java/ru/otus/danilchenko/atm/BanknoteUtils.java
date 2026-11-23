package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.INominal;

public class BanknoteUtils {
    public static int getNominalValue(INominal nominal) {
        return nominal.getNominalValue();
    }
}
