package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.cell.IGotCell;

import java.util.Map;
import java.util.Objects;

public class BanknoteReturning implements IBanknoteReturning {
    private final Map<Class<? extends IBanknote>, IGotCell> cells;

    public BanknoteReturning(Map<Class<? extends IBanknote>, IGotCell> cells) {
        Objects.requireNonNull(cells);
        this.cells = cells;
    }

    @Override
    public IBanknote[] returnBanknotes(int returnedSum) {
        //todo реализовать логику возрата как можно меньшего кол-ва банкнот из ячеек
        return new IBanknote[0];
    }
}
