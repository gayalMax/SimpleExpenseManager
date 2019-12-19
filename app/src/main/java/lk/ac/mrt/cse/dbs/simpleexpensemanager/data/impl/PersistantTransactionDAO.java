package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class PersistantTransactionDAO implements TransactionDAO {
    private final List<Transaction> transactions;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    DatabaseHelper databaseHelper;


    public PersistantTransactionDAO(DatabaseHelper databaseHelper) {
        transactions = new LinkedList<>();
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        databaseHelper.insertDataToTransaction(date,accountNo,expenseType,amount);
//        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
//        transactions.add(date, accountNo, expenseType, amount);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {
        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllDataFromTransaction();
        while(cursor.moveToNext()){
            Date date = dateFormat.parse(cursor.getString(0));
            ExpenseType type = cursor.getString(2)=="Expense"?ExpenseType.EXPENSE:ExpenseType.INCOME;
            Transaction trans = new Transaction(date,cursor.getString(1),type,Integer.parseInt(cursor.getString(3)));
            transactions.add(trans);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllDataFromTransactionWithLimit(limit);
        Date date = new Date();
        while(cursor.moveToNext()){
            try {
                date = dateFormat.parse(cursor.getString(0));
            } catch (ParseException e) {
//                e.printStackTrace();
            }
            ExpenseType type;
            if(cursor.getString(2).equals("Expense"))
                type = ExpenseType.EXPENSE;
            else
                type = ExpenseType.INCOME;
//            ExpenseType type = (cursor.getString(2)=="Expense")?ExpenseType.EXPENSE:ExpenseType.INCOME;
            Transaction trans = new Transaction(date,cursor.getString(1),type,Integer.parseInt(cursor.getString(3)));
            transactions.add(trans);
        }
        return transactions;
//        int size = transactions.size();
//        if (size <= limit) {
//            return transactions;
//        }
//        // return the last <code>limit</code> number of transaction logs
//        return transactions.subList(size - limit, size);
    }

}

