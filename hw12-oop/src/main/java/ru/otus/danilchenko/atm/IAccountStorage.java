package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.account.IAccount;

public interface IAccountStorage {
    IAccount getAccount(long accountId);
}
