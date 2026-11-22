package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;

import java.util.Objects;

public class Cashpoint implements IBanknoteReceiving, IChangeReturning {
    private IBanknoteReceiving banknoteReceiver;
    private IChangeReturning changeReturner;

    public Cashpoint(IBanknoteReceiving banknoteReceiver, IChangeReturning changeReturner) {
        Objects.requireNonNull(banknoteReceiver);
        this.banknoteReceiver = banknoteReceiver;
        this.changeReturner = changeReturner;
    }

    @Override
    public void receiveCache(IBanknote banknote) {
        banknoteReceiver.receiveCache(banknote);
    }

    @Override
    public IBanknote[] returnChange() {
        return changeReturner.returnChange();
    }
}
