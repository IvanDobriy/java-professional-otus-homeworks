package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.account.IAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountStorage implements IAccountStorage {
    Map<Long, IAccount> storage;

    public AccountStorage() {
        storage = new HashMap<>();
    }

    public AccountStorage(Map<Long, IAccount> storage) {
        Objects.requireNonNull(storage);
        this.storage = storage;
    }

    @Override
    public IAccount getAccount(long accountId) {
        return storage.get(accountId);
    }
}
