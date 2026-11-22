package ru.otus.danilchenko.atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.danilchenko.atm.banknote.Banknote;
import ru.otus.danilchenko.atm.banknote.Nominal;
import ru.otus.danilchenko.atm.cell.Cell;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BanknoteReturningTests {
    @Mock
    private Cell cell10;
    @Mock
    private Cell cell100;


    @Test
    void positive_test() {
        final var banknote1 = new Banknote(new Nominal(10));
        final var banknote2 = new Banknote(new Nominal(100));
        final var banknoteReturning = new BanknoteReturning(
                Map.of(banknote1.getNominal(), cell10, banknote2.getNominal(), cell100)
        );
        banknoteReturning.returnBanknotes(10);
    }

}
