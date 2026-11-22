package ru.otus.danilchenko.atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.danilchenko.atm.account.CurrentAccount;
import ru.otus.danilchenko.atm.banknote.Banknote;
import ru.otus.danilchenko.atm.banknote.Nominal;
import ru.otus.danilchenko.atm.cell.Cell;

import java.util.Map;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BanknoteReceivingTests {

    @Mock
    private Cell cell10;

    @Mock
    private Cell cell100;

    @Mock
    private CurrentAccount currentAccount;

    private BanknoteReceiving banknoteReceiving;

    @Test
    void positive_test() {
        final var banknote1 = new Banknote(new Nominal(10));
        final var banknote2 = new Banknote(new Nominal(100));
        Mockito.doNothing().when(cell10).putBanknote(banknote1);
        Mockito.doNothing().when(cell100).putBanknote(banknote2);

        Mockito.doNothing().when(currentAccount).addAmountOfMoney(10);

        banknoteReceiving = new BanknoteReceiving(
                Map.of(banknote1.getNominal(), cell10,
                        banknote2.getNominal(), cell100), currentAccount);


        banknoteReceiving.receiveBanknote(banknote1);
        banknoteReceiving.receiveBanknote(banknote2);

        Mockito.verify(cell10, Mockito.times(1)).putBanknote(banknote1);
        Mockito.verify(cell10, Mockito.times(0)).putBanknote(banknote2);

        Mockito.verify(currentAccount, Mockito.times(1)).addAmountOfMoney(10);
        Mockito.verify(currentAccount, Mockito.times(1)).addAmountOfMoney(100);

        Mockito.verify(cell100, Mockito.times(0)).putBanknote(banknote1);
        Mockito.verify(cell100, Mockito.times(1)).putBanknote(banknote2);
    }
}
