package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistantAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistantTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistantDemoExpenseManager extends ExpenseManager {
    DatabaseHelper databaseHelper;

    public PersistantDemoExpenseManager(Context applicationContext){
        System.out.println("fdgsdfgfsg");
        databaseHelper = new DatabaseHelper(applicationContext);
        setup();
    }

    public void setup() {
        /*** Begin generating dummy data for In-Memory implementation ***/

        TransactionDAO persistantTransactionDAO = new PersistantTransactionDAO(databaseHelper);
        setTransactionsDAO(persistantTransactionDAO);

        AccountDAO persistantAccountDAO = new PersistantAccountDAO(databaseHelper);
        setAccountsDAO(persistantAccountDAO);

        // dummy data
//        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
//        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
//        getAccountsDAO().addAccount(dummyAcct1);
//        getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }
}

