package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;

public class ChangeReturner implements IChangeReturning{

    @Override
    public IBanknote[] returnChange() {
        return new IBanknote[0];
    }
}
