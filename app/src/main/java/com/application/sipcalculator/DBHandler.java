package com.application.sipcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    int sipId ;
    String sipActivityDate;
    String investment;
    String rate;
    String time ;
    String totalInvestment;
    String totalReturn ;
    String returnAmount;

    int loanId;
    String loanActivityDate;
    String loanAmount;
    String loanRate;
    String loanTime;
    String loanEMI;
    String loanInterestPayable;
    String loanTotalAmount;
    public DBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sip = "CREATE TABLE " + Params.SIP_TABLE_NAME +
                " (" + Params.KEY_SIP_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Params.KEY_SIP_ACTIVITY_DATE + "  TEXT, " +
                Params.KEY_INVESTMENT + "  TEXT, " +
                Params.KEY_RATE + "  TEXT, " +
                Params.KEY_TIME + "  TEXT, " +
                Params.KEY_TOTAL_INVESTMENT + "  TEXT, " +
                Params.KEY_TOTAL_RETURN + "  TEXT, " +
                Params.KEY_RETURN_AMOUNT + "  TEXT" +
                ")";

        String create_loan = "CREATE TABLE " + Params.LOAN_TABLE_NAME +
                " (" + Params.KEY_LOAN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Params.KEY_LOAN_ACTIVITY_DATE + "  TEXT, " +
                Params.KEY_lOAN_AMOUNT + "  TEXT, " +
                Params.KEY_LOAN_RATE + "  TEXT, " +
                Params.KEY_LOAN_TIME + "  TEXT, " +
                Params.KEY_EMI + "  TEXT, " +
                Params.KEY_INTEREST_PAYABLE + "  TEXT, " +
                Params.KEY_LOAN_TOTAL_AMOUNT + "  TEXT" +
                ")";


        db.execSQL(create_sip);
        db.execSQL(create_loan);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Params.SIP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Params.LOAN_TABLE_NAME);
        onCreate(db);
    }

    public void addSIPHistory(SIPData sipData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_SIP_ACTIVITY_DATE, sipData.getSipActivityDate());
        values.put(Params.KEY_INVESTMENT, sipData.getSipInvestment());
        values.put(Params.KEY_RATE, sipData.getSipRate());
        values.put(Params.KEY_TIME, sipData.getSipTime());
        values.put(Params.KEY_TOTAL_INVESTMENT, sipData.getSipTotalInvestment());
        values.put(Params.KEY_TOTAL_RETURN, sipData.getSipTotalReturn());
        values.put(Params.KEY_RETURN_AMOUNT, sipData.getSipReturnAmount());
        db.insert(Params.SIP_TABLE_NAME, null, values);
        Log.d("db", "Successfully insert");
        db.close();
    }

    public void addLoanHistory(LoanData loanData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_LOAN_ACTIVITY_DATE, loanData.getLoanActivityDate());
        values.put(Params.KEY_lOAN_AMOUNT, loanData.getLoanAmount());
        values.put(Params.KEY_LOAN_RATE, loanData.getLoanRate());
        values.put(Params.KEY_LOAN_TIME, loanData.getLoanTime());
        values.put(Params.KEY_LOAN_TOTAL_AMOUNT, loanData.getLoanTotalAmount());
        values.put(Params.KEY_EMI, loanData.getLoanEMI());
        values.put(Params.KEY_INTEREST_PAYABLE, loanData.getLoanInterestPayable());
        db.insert(Params.LOAN_TABLE_NAME, null, values);
        Log.d("db", "Successfully inserted");
        db.close();
    }

    public void fetchSIPData(){
        String selectSIPQuery = "SELECT * FROM " + Params.SIP_TABLE_NAME;

        // Execute the query using your preferred database framework or API
        // For example, using SQLiteOpenHelper in Android:

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectSIPQuery, null);

        // Iterate through the cursor to access the fetched data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values using column indexes or column names
                sipId = cursor.getInt(cursor.getColumnIndex(Params.KEY_SIP_ID));
                sipActivityDate = cursor.getString(cursor.getColumnIndex(Params.KEY_SIP_ACTIVITY_DATE));
                investment = cursor.getString(cursor.getColumnIndex(Params.KEY_INVESTMENT));
                rate = cursor.getString(cursor.getColumnIndex(Params.KEY_RATE));
                time = cursor.getString(cursor.getColumnIndex(Params.KEY_TIME));
                totalInvestment = cursor.getString(cursor.getColumnIndex(Params.KEY_TOTAL_INVESTMENT));
                totalReturn = cursor.getString(cursor.getColumnIndex(Params.KEY_TOTAL_RETURN));
                returnAmount = cursor.getString(cursor.getColumnIndex(Params.KEY_RETURN_AMOUNT));

                // Use the retrieved values as needed
                // ...

            } while (cursor.moveToNext());
        }

        // Close the cursor and database connection when finished
        cursor.close();
        db.close();
    }
    public void fetchLoanData(){
        String selectLoanQuery = "SELECT * FROM " + Params.LOAN_TABLE_NAME;

        // Execute the query using your preferred database framework or API
        // For example, using SQLiteOpenHelper in Android:

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectLoanQuery, null);

        // Iterate through the cursor to access the fetched data
        if (cursor.moveToNext()) {
            do {
                // Retrieve column values using column indexes or column names
                loanId = cursor.getInt(cursor.getColumnIndex(Params.KEY_LOAN_ID));
                loanActivityDate = cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_ACTIVITY_DATE));
                loanAmount = cursor.getString(cursor.getColumnIndex(Params.KEY_lOAN_AMOUNT));
                loanRate = cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_RATE));
                loanTime = cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_TIME));
                loanEMI = cursor.getString(cursor.getColumnIndex(Params.KEY_EMI));
                loanInterestPayable = cursor.getString(cursor.getColumnIndex(Params.KEY_INTEREST_PAYABLE));
                loanTotalAmount = cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_TOTAL_AMOUNT));

                // Use the retrieved values as needed
                // ...

            } while (cursor.moveToNext());
        }

        // Close the cursor and database connection when finished
        cursor.close();
        db.close();
    }
}


