package ru.otus.danilchenko.atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.danilchenko.atm.banknote.BanknoteNominal10;
import ru.otus.danilchenko.atm.banknote.BanknoteNominal100;
import ru.otus.danilchenko.atm.cell.Cell;

import java.util.Map;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BanknoteReceivingTests {

    @Mock
    private Cell cell10;

    @Mock
    private Cell cell100;

    private BanknoteReceiving banknoteReceiving;

    @Test
    void positive_test() {
        final var banknote1 = new BanknoteNominal10();
        final var banknote2 = new BanknoteNominal100();
        Mockito.doNothing().when(cell10).putBanknote(banknote1);
        Mockito.doNothing().when(cell100).putBanknote(banknote2);

        banknoteReceiving = new BanknoteReceiving(
                Map.of(BanknoteNominal10.class, cell10,
                        BanknoteNominal100.class, cell100));

        banknoteReceiving.receiveBanknote(banknote1);
        banknoteReceiving.receiveBanknote(banknote2);

        Mockito.verify(cell10, Mockito.times(1)).putBanknote(banknote1);
        Mockito.verify(cell10, Mockito.times(0)).putBanknote(banknote2);

        Mockito.verify(cell100, Mockito.times(0)).putBanknote(banknote1);
        Mockito.verify(cell100, Mockito.times(1)).putBanknote(banknote2);

    }
}
