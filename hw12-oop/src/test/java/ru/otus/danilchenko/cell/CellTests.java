package ru.otus.danilchenko.cell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.danilchenko.atm.banknote.BanknoteNominal10;
import ru.otus.danilchenko.atm.banknote.BanknoteNominal100;
import ru.otus.danilchenko.atm.banknote.IBanknote;
import ru.otus.danilchenko.atm.cell.Cell;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CellTests {

    @Test
    void positive_test() {
        final List<IBanknote> banknotes = new ArrayList<>();
        final var cell = new Cell(banknotes);
        final var banknote1 = new BanknoteNominal10();
        final var banknote2 = new BanknoteNominal100();
        cell.putBanknote(banknote1);
        cell.putBanknote(banknote2);
        Assertions.assertTrue(banknotes.contains(banknote1));
        Assertions.assertTrue(banknotes.contains(banknote2));
        Assertions.assertEquals(2, banknotes.size());
        Assertions.assertEquals(2, cell.getSize());

        var cellBanknote = cell.getBanknote();
        Assertions.assertEquals(banknote2, cellBanknote);
        Assertions.assertEquals(1, cell.getSize());
        Assertions.assertEquals(1, banknotes.size());

        cellBanknote  = cell.getBanknote();
        Assertions.assertEquals(banknote1, cellBanknote);
        Assertions.assertEquals(0, cell.getSize());
        Assertions.assertEquals(0, banknotes.size());
    }
}
