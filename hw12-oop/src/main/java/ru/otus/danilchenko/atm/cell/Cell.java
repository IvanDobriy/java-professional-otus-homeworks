package ru.otus.danilchenko.atm.cell;

import ru.otus.danilchenko.atm.banknote.IBanknote;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell implements IPutCell, IGotCell {
    final List<IBanknote> banknotes;

    public Cell() {
        banknotes = new ArrayList<>();
    }

    public Cell(List<IBanknote> banknotes) {
        Objects.requireNonNull(banknotes);
        this.banknotes = banknotes;
    }


    @Override
    public void putBanknote(IBanknote banknote) {
        banknotes.add(banknote);
    }

    @Override
    public IBanknote getBanknote() {
        return banknotes.removeLast();
    }

    @Override
    public int getSize() {
        return banknotes.size();
    }
}
