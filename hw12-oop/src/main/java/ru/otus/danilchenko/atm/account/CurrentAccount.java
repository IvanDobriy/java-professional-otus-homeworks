package ru.otus.danilchenko.atm.account;

public class CurrentAccount implements IAccount {

    private final long id;

    private int amountOfMoney;

    public CurrentAccount(long id, int amountOfMoney) {
        this.id = id;
        this.amountOfMoney = amountOfMoney;
    }


    @Override
    public long getId() {
        return id;
    }


    @Override
    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    @Override
    public void addAmountOfMoney(int sum) {
        amountOfMoney += sum;
    }

    @Override
    public void removeAmountOfMoney(int sum) {
        amountOfMoney -= sum;
    }
}
