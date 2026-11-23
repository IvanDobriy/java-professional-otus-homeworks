package ru.otus.danilchenko.atm.account;

public interface IAccount {
    long getId();
    int getAmountOfMoney();
    void addAmountOfMoney(int sum);
    void removeAmountOfMoney(int sum);
}
