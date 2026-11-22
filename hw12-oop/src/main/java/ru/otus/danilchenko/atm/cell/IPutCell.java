package ru.otus.danilchenko.atm.cell;

import ru.otus.danilchenko.atm.banknote.IBanknote;

public interface IPutCell extends  ISizedCell {
    void putBanknote(IBanknote banknote);
}
