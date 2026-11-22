package ru.otus.danilchenko.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.danilchenko.atm.account.CurrentAccount;
import ru.otus.danilchenko.atm.banknote.Banknote;
import ru.otus.danilchenko.atm.banknote.IBanknote;
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

    @Mock
    private CurrentAccount currentAccount;


    @Test
    void positive_test() {
        final var banknote1 = new Banknote(new Nominal(10));
        final var banknote2 = new Banknote(new Nominal(100));

        final var banknoteReturning = new BanknoteReturning(
                Map.of(banknote1.getNominal(), cell10, banknote2.getNominal(), cell100), currentAccount
        );

        Mockito.when(currentAccount.getAmountOfMoney()).thenReturn(110);
        Mockito.doNothing().when(currentAccount).removeAmountOfMoney(110);

        Mockito.when(cell10.getBanknote()).thenReturn(banknote1);
        Mockito.when(cell10.getSize()).thenReturn(1, 0);
        Mockito.when(cell100.getBanknote()).thenReturn(banknote2);
        Mockito.when(cell100.getSize()).thenReturn(1, 0);

        
        final var result  = banknoteReturning.returnBanknotes(110);
        Assertions.assertEquals(2, result.length);
        Assertions.assertArrayEquals(new IBanknote[]{banknote2, banknote1}, result);
    }

}
