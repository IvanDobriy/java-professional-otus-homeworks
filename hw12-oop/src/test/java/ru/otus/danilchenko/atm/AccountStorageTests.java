package ru.otus.danilchenko.atm;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.danilchenko.atm.account.CurrentAccount;
import ru.otus.danilchenko.atm.account.IAccount;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountStorageTests {

    @Test
    void positive_test() {
        final IAccount account = new CurrentAccount(1, 110);
        final Map<Long, IAccount> storageMap = Map.of(account.getId(), account);
        final IAccountStorage storage = new AccountStorage(storageMap);
        Assertions.assertEquals(account, storage.getAccount(1));
    }
}
