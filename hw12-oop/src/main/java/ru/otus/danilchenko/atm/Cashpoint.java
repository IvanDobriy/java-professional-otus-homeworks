package ru.otus.danilchenko.atm;

import ru.otus.danilchenko.atm.account.IAccount;
import ru.otus.danilchenko.atm.banknote.IBanknote;

import java.util.Objects;

public class Cashpoint implements IBanknoteReceiving, IBanknoteReturning, ILogin, ILogout {
    private IBanknoteReceiving banknoteReceiving;
    private IBanknoteReturning banknoteReturning;
    private IAccountStorage accountStorage;
    private IAccount account;

    public Cashpoint(IBanknoteReceiving banknoteReceiving, IBanknoteReturning banknoteReturning, IAccountStorage accountStorage) {
        Objects.requireNonNull(banknoteReceiving);
        Objects.requireNonNull(banknoteReturning);
        Objects.requireNonNull(accountStorage);
        
        this.banknoteReceiving = banknoteReceiving;
        this.banknoteReturning = banknoteReturning;
        this.accountStorage = accountStorage;
        account = null;
    }

    @Override
    public void receiveBanknote(IBanknote banknote) {
        Objects.requireNonNull(account, "to receive banknote first need login");
        account.addAmountOfMoney(banknote.getNominal().getNominalValue());
        banknoteReceiving.receiveBanknote(banknote);
    }

    @Override
    public IBanknote[] returnBanknotes(int returnedSum) {
        Objects.requireNonNull(account, "to return banknotes first need login");
        if(returnedSum  > account.getAmountOfMoney() ){
            throw new RuntimeException("returned sum more than account money");
        }
        final var banknotes = banknoteReturning.returnBanknotes(returnedSum);
        for(var banknote: banknotes){
            account.removeAmountOfMoney(banknote.getNominal().getNominalValue());
        }
        return banknotes;
    }

    @Override
    public void login(long id) {
        if(account != null && id != account.getId()){
            throw new RuntimeException("The ATM is occupied by another user");
        }
        account = accountStorage.getAccount(id);
    }

    @Override
    public void logout() {
        account = null;
    }
}
