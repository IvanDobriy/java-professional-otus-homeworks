package ru.otus.danilchenko;

import ru.otus.danilchenko.atm.AccountStorage;
import ru.otus.danilchenko.atm.BanknoteReceiving;
import ru.otus.danilchenko.atm.BanknoteReturning;
import ru.otus.danilchenko.atm.Cashpoint;
import ru.otus.danilchenko.atm.banknote.Banknote;
import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.banknote.INominal;
import ru.otus.danilchenko.atm.banknote.Nominal;
import ru.otus.danilchenko.atm.cell.Cell;
import ru.otus.danilchenko.atm.cell.IGotCell;
import ru.otus.danilchenko.atm.cell.IPutCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final INominal nominal10 = new Nominal(10);
    private static final INominal nominal50 = new Nominal(50);
    private static final INominal nominal100 = new Nominal(100);

    private static final IBanknote banknote10 = new Banknote(nominal10);
    private static final IBanknote banknote50 = new Banknote(nominal50);
    private static final IBanknote banknote100 = new Banknote(nominal100);

    private static final Cell cell10 = new Cell(new ArrayList<>());
    private static final Cell cell50 = new Cell(new ArrayList<>());
    private static final Cell cell100 = new Cell(new ArrayList<>());

    public static void main(String[] args) {

        fillCell(cell10, banknote10, 10);
        fillCell(cell50, banknote50, 20);
        fillCell(cell100, banknote100, 30);

        final var banknoteReceivingCells = new HashMap<INominal, IPutCell>();
        banknoteReceivingCells.put(nominal10, cell10);
        banknoteReceivingCells.put(nominal50, cell50);
        banknoteReceivingCells.put(nominal100, cell100);

        final var banknoteReturningCells = new HashMap<INominal, IGotCell>();
        banknoteReturningCells.put(nominal10, cell10);
        banknoteReturningCells.put(nominal50, cell50);
        banknoteReturningCells.put(nominal100, cell100);


        final var accountStorage = new AccountStorage();
        final var banknoteReceiving = new BanknoteReceiving(banknoteReceivingCells);
        final var banknoteReturning = new BanknoteReturning(banknoteReturningCells);
        final var cashpoint = new Cashpoint(banknoteReceiving, banknoteReturning, accountStorage);
        cashpoint.login(1);

    }



    private static void fillCell(Cell cell, IBanknote banknote, int number) {
        for (int i = 0; i < number; i++) {
            cell.putBanknote(banknote);
        }
    }
}
