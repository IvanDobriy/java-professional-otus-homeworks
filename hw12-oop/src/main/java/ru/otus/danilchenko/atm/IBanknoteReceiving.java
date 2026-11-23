package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;

public interface IBanknoteReceiving {
    void receiveBanknote(IBanknote banknote);
}
