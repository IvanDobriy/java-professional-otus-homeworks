package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.banknote.INominal;
import ru.otus.danilchenko.atm.cell.IPutCell;

import java.util.Map;
import java.util.Objects;

public class BanknoteReceiving implements IBanknoteReceiving {

    private final Map<INominal, ? extends IPutCell> cells;

    public BanknoteReceiving(Map<INominal, ? extends IPutCell> cells) {
        Objects.requireNonNull(cells);
        this.cells = cells;
    }

    @Override
    public void receiveBanknote(IBanknote banknote) {
        Objects.requireNonNull(banknote);
        final IPutCell cell = cells.get(banknote.getNominal());
        if (cell == null) {
            throw new IllegalArgumentException(String.format("cell for current banknote  not found: %s", banknote.getNominal()));
        }
        cell.putBanknote(banknote);
    }
}
