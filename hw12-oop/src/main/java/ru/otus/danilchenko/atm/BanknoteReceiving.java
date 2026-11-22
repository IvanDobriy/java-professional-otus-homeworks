package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.account.IAccount;
import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.banknote.INominal;
import ru.otus.danilchenko.atm.cell.IPutCell;

import java.util.Map;
import java.util.Objects;

public class BanknoteReceiving implements IBanknoteReceiving {

    private final Map<INominal, IPutCell> cells;
    private final IAccount account;

    public BanknoteReceiving(Map<INominal, IPutCell> cells, IAccount account) {
        Objects.requireNonNull(cells);
        Objects.requireNonNull(account);
        this.account = account;
        this.cells = cells;
    }

    @Override
    public void receiveBanknote(IBanknote banknote) {
        Objects.requireNonNull(banknote);
        final IPutCell cell = cells.get(banknote.getNominal());
        if (cell == null) {
            throw new IllegalArgumentException(String.format("cell for current banknote  not found: %s", banknote.getNominal()));
        }
        account.addAmountOfMoney(banknote.getNominal().getNominalValue()); //todo need  fix that
        cell.putBanknote(banknote);
    }
}
