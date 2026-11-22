package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.cell.IPutCell;

import java.util.HashMap;
import java.util.Objects;

public class BanknoteReceiving implements IBanknoteReceiving {

    private final HashMap<Class<? extends IBanknote>, IPutCell> cells;

    public BanknoteReceiving(HashMap<Class<? extends IBanknote>, IPutCell> cells) {
        Objects.requireNonNull(cells);
        this.cells = cells;
    }

    @Override
    public void receiveBanknote(IBanknote banknote) {
        Objects.requireNonNull(banknote);
        final IPutCell cell = cells.get(banknote.getClass());
        if (cell == null) {
            throw new IllegalArgumentException(String.format("cell for current banknote  not found: %s", banknote.getNominal()));
        }
        cell.putBanknote(banknote);
    }
}
