package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;

import java.util.Objects;

public class Cashpoint implements IBanknoteReceiving, IBanknoteReturning {
    private IBanknoteReceiving banknoteReceiving;
    private IBanknoteReturning banknoteReturning;

    public Cashpoint(IBanknoteReceiving banknoteReceiving, IBanknoteReturning banknoteReturning) {
        Objects.requireNonNull(banknoteReceiving);
        Objects.requireNonNull(banknoteReturning);
        this.banknoteReceiving = banknoteReceiving;
        this.banknoteReturning = banknoteReturning;
    }

    @Override
    public void receiveBanknote(IBanknote banknote) {
        banknoteReceiving.receiveBanknote(banknote);
    }

    @Override
    public IBanknote[] returnBanknotes(int returnedSum) {
        return banknoteReturning.returnBanknotes(returnedSum);
    }
}
