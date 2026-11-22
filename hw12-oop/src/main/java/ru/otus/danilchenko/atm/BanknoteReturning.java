package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.banknote.INominal;
import ru.otus.danilchenko.atm.cell.IGotCell;

import java.util.Map;
import java.util.Objects;

public class BanknoteReturning implements IBanknoteReturning {
    private final Map<INominal, IGotCell> cells;

    public BanknoteReturning(Map<INominal, IGotCell> cells) {
        Objects.requireNonNull(cells);
        this.cells = cells;
    }

    @Override
    public IBanknote[] returnBanknotes(int returnedSum) {
//        //todo реализовать логику возрата как можно меньшего кол-ва банкнот из ячеек
//        cells.forEach(banknoteType -> {
//
//        });
        return new IBanknote[0];
    }
}
