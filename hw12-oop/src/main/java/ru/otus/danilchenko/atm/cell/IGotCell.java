package ru.otus.danilchenko.atm.cell;

import ru.otus.danilchenko.atm.banknote.IBanknote;

public interface IGotCell extends ISizedCell {
    IBanknote getBanknote();
}
