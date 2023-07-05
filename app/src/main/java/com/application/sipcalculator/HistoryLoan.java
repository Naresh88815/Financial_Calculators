package com.application.sipcalculator;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryLoan extends AppCompatActivity {
    ArrayList<String>  loanActivityDate, loanAmount, loanRate, loanTime, loanEMI, loanInterestPayable, loanTotalAmount;
    RecyclerView recyclerView;
    DBHandler dbHandler;
    HistoryLoanAdapter historyAdapter;

    AppCompatButton clrLoanBtn;

    TextView noHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_loan);
        dbHandler= new DBHandler(this);

        noHistory=findViewById(R.id.noHistory);
        noHistory.setVisibility(View.INVISIBLE);


        loanActivityDate= new ArrayList<>();
        loanAmount=new ArrayList<>();
        loanRate=new ArrayList<>();
        loanTime=new ArrayList<>();
        loanEMI=new ArrayList<>();
        loanInterestPayable=new ArrayList<>();
        loanTotalAmount=new ArrayList<>();
        recyclerView=findViewById(R.id.loanHistoryList);
        historyAdapter=new HistoryLoanAdapter(this,loanActivityDate, loanAmount, loanRate,loanTime, loanEMI, loanInterestPayable,loanTotalAmount);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        display();

        clrLoanBtn=findViewById(R.id.clear_loan);
        clrLoanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.clearLoanHistory();
                recyclerView.setVisibility(View.GONE);
                noHistory.setVisibility(View.VISIBLE);
            }
        });
    }

    public void display(){
        dbHandler.fetchLoanData();
        Cursor cursor= dbHandler.cursor;
        if (cursor.getCount()==0){
            noHistory.setVisibility(View.VISIBLE);
        }
        else {
            while (cursor.moveToNext()) {
                int loanAmt=cursor.getInt(cursor.getColumnIndex(Params.KEY_lOAN_AMOUNT));
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
                decimalFormat.applyPattern("#,##,###");
                String formattedLoanAmt = decimalFormat.format(loanAmt);

                int emi=cursor.getInt(cursor.getColumnIndex(Params.KEY_EMI));
                String formattedEmi=decimalFormat.format(emi);

                int interestPayable= cursor.getInt(cursor.getColumnIndex(Params.KEY_INTEREST_PAYABLE));
                String formattedInterestPayable=decimalFormat.format(interestPayable);

                int totalLoan=cursor.getInt(cursor.getColumnIndex(Params.KEY_LOAN_TOTAL_AMOUNT));
                String formattedTotalLoan=decimalFormat.format(totalLoan);



                loanActivityDate.add(cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_ACTIVITY_DATE)));
                loanAmount.add(formattedLoanAmt);
                loanRate.add(cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_RATE)));
                loanTime.add(cursor.getString(cursor.getColumnIndex(Params.KEY_LOAN_TIME)));
                loanEMI.add(formattedEmi);
                loanInterestPayable.add(formattedInterestPayable);
                loanTotalAmount.add(formattedTotalLoan);

//                Log.d("db", "Successfully extracted");
            }
        }

        cursor.close();
        dbHandler.db.close();
    }
}