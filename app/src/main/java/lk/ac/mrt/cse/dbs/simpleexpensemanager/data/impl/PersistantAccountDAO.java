package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistantAccountDAO implements AccountDAO {
//    private final Map<String, Account> accounts;
    DatabaseHelper databaseHelper;


    public PersistantAccountDAO(DatabaseHelper databaseHelper) {
//        this.accounts = new HashMap<>();
        this.databaseHelper = databaseHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
//        System.out.println("Error here");
        Cursor cursor = databaseHelper.getAccountNumbersFromAccount();
        List<String> accountNumberList = new ArrayList<>();

        while(cursor.moveToNext()){
//            Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
            accountNumberList.add(cursor.getString(0));
        }

        return accountNumberList;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor cursor = databaseHelper.getAllDataFromAccount();
        ArrayList<Account> accountList = new ArrayList<>();

        while(cursor.moveToNext()){
            Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
            accountList.add(account);
        }

        return accountList;

    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        List<String> accountNumberList = getAccountNumbersList();
        if(accountNumberList.contains(accountNo)){
            Cursor cursor = databaseHelper.getAccountFromAccount(accountNo);
            return new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
        databaseHelper.insertDataToAccount(account.getAccountNo(),account.getBankName(),account.getAccountHolderName(),account.getBalance());
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        List<String> accountNumberList = getAccountNumbersList();
        if (!accountNumberList.contains(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        databaseHelper.removeAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        List<String> accountNumberList = getAccountNumbersList();
        if (!accountNumberList.contains(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }

        Cursor cursor = databaseHelper.getAccountFromAccount(accountNo);
        Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                databaseHelper.updateBalance(accountNo,account.getBalance() - amount);
//                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                databaseHelper.updateBalance(accountNo,account.getBalance() + amount);
                break;
        }
    }
}
