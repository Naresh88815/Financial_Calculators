package com.application.sipcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    Cursor sipCursor, cursor;
    SQLiteDatabase db;

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
        Log.d("db", "Successfully inserted");
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
        db = this.getReadableDatabase();
        sipCursor = db.rawQuery(selectSIPQuery, null);
    }

    public void fetchLoanData() {
        String selectLoanQuery = "SELECT * FROM " +  Params.LOAN_TABLE_NAME;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectLoanQuery, null);
    }
    public void clearSIPHistory(){
        String clearSIPQuery="DELETE FROM "+ Params.SIP_TABLE_NAME;
        db=this.getWritableDatabase();
        db.execSQL(clearSIPQuery);
    }

    public void clearLoanHistory(){
        String clearLoanQuery="DELETE FROM "+ Params.LOAN_TABLE_NAME;
        db=this.getWritableDatabase();
        db.execSQL(clearLoanQuery);
    }
}


