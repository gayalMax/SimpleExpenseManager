package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ExpenseManager.db";
    private static final String TABLE1_NAME = "transaction_table";
    private static final String TABLE2_NAME = "account_table";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    /*
log table var
 */
    private static final String T1COL1 = "id";
    private static final String T1COL2 = "date";
    private static final String T1COL3 = "account_no";
    private static final String T1COL4 = "type";
    private static final String T1COL5 = "amount";
/*
account table var
 */
    private static final String T2COL1 = "account_no";
    private static final String T2COL2 = "bank";
    private static final String T2COL3 = "holder";
    private static final String T2COL4 = "balance";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 5);
//        System.out.println("constturcot");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE1_NAME+
                "("+T1COL1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+T1COL2+" DATE,"+T1COL3+" INTEGER,"+T1COL4+" TEXT,"+T1COL5+" TEXT)");
        System.out.println("qwertyuio");
        db.execSQL("create table "+TABLE2_NAME+
                "("+T2COL1+" INTEGER PRIMARY KEY,"+T2COL2+" TEXT,"+T2COL3+" TEXT,"+T2COL4+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);
    }

    public boolean insertDataToTransaction(Date date, String account_no, ExpenseType type, Double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(T1COL2,dateFormat.format(date));
        contentValues.put(T1COL3,account_no);
        contentValues.put(T1COL4,(type ==ExpenseType.EXPENSE)? "Expense":"Income");
        contentValues.put(T1COL5,amount);

        long result = db.insert(TABLE1_NAME,null,contentValues);
        return result != -1;
    }

    public boolean insertDataToAccount(String account_no,String bank,String holder,Double balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2COL1,account_no);
        contentValues.put(T2COL2,bank);
        contentValues.put(T2COL3,holder);
        contentValues.put(T2COL4,balance);

        long result = db.insert(TABLE2_NAME,null,contentValues);
        return result != -1;
    }

    public Cursor getAllDataFromTransaction(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE1_NAME,null);
        return result;
    }

    public Cursor getAllDataFromTransactionWithLimit(int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE1_NAME+" LIMIT "+String.valueOf(limit),null);
        return result;
    }

//    public boolean addTransaction(String date,String accountNo,String expenseType,Double amount){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(T1COL2,date);
//        contentValues.put(T1COL3,accountNo);
//        contentValues.put(T1COL4,expenseType);
//        contentValues.put(T1COL5,amount);
//
//        long result = db.insert(TABLE1_NAME,null,contentValues);
//        return result != -1;
//    }

    public Cursor getAllDataFromAccount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE2_NAME,null);
        return result;
    }

    public Cursor getAccountNumbersFromAccount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select "+T2COL1+" from "+TABLE2_NAME,null);
        return result;
    }

    public Cursor getAccountFromAccount(String accountNo){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE2_NAME+" WHERE "+T2COL1+"="+accountNo,null);
        return result;
    }

    public void removeAccount(String accountNo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("delete * from "+TABLE2_NAME+" WHERE accountNo="+accountNo,null);
    }

    public void updateBalance(String accountNo,double balance){
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("update "+TABLE2_NAME+" SET "+T2COL4+"="+balance+" WHERE "+T2COL1+"="+accountNo,null);
    }




}
