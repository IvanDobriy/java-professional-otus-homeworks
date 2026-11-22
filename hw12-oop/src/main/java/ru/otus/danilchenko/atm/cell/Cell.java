package ru.otus.danilchenko.atm.cell;

import ru.otus.danilchenko.atm.banknote.IBanknote;

import java.util.ArrayList;
import java.util.List;

public class Cell implements IPutCell, IGotCell {
    final List<IBanknote> banknotes = new ArrayList<IBanknote>();

    @Override
    public void putBanknote(IBanknote banknote) {
        banknotes.add(banknote);
    }

    @Override
    public IBanknote getBanknotes() {
        return banknotes.removeLast();
    }

    @Override
    public int getSize() {
        return banknotes.size();
    }
}
