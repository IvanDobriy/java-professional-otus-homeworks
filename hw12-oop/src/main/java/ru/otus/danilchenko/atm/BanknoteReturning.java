package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.account.IAccount;
import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.banknote.INominal;
import ru.otus.danilchenko.atm.cell.IGotCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BanknoteReturning implements IBanknoteReturning {
    private final List<Map.Entry<INominal, IGotCell>> cells;

    public BanknoteReturning(Map<INominal, IGotCell> cells) {
        Objects.requireNonNull(cells);
        this.cells = cells.entrySet()
                .stream()
                .sorted(
                        (el1, el2) ->
                                el2.getKey().getNominalValue() - el1.getKey().getNominalValue()
                ).toList();


    }

    @Override
    public IBanknote[] returnBanknotes(int returnedSum) {
        List<IBanknote> banknotes = new ArrayList<>();
        final AtomicInteger rest = new AtomicInteger(returnedSum);
        cells.forEach(el -> {
            INominal nominal = el.getKey();
            IGotCell cell = el.getValue();
            int num = rest.get() / nominal.getNominalValue();
            if (num > 0 && cell.getSize() > 0) {
                banknotes.add(cell.getBanknote());
                rest.set(rest.get() % nominal.getNominalValue());
            }
        });
        return banknotes.toArray(IBanknote[]::new);
    }
}
