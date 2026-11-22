package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;

public class BanknoteReturning implements IBanknoteReturning {


    @Override
    public IBanknote[] returnBanknotes(int returnedSum) {
        return new IBanknote[0];
    }
}
