package com.application.sipcalculator;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class HistorySIP extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> sipActivityDate, sipInvestment, sipRate, sipTime,  sipWealthGain, sipTotalReturn,sipTotalInvestment;
    DBHandler dbHandler;
    HistorySIPAdapter historyAdapter;

    AppCompatButton  clrSIPBtn;

    TextView noHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_sip);
        dbHandler= new DBHandler(this);

        noHistory=findViewById(R.id.noHistory);
        noHistory.setVisibility(View.INVISIBLE);


        sipActivityDate= new ArrayList<>();
        sipInvestment= new ArrayList<>();
        sipRate= new ArrayList<>();
        sipTime= new ArrayList<>();
        sipTotalInvestment= new ArrayList<>();
        sipTotalReturn= new ArrayList<>();
        sipWealthGain= new ArrayList<>();
        recyclerView=findViewById(R.id.historyList);
        historyAdapter=new HistorySIPAdapter(this, sipActivityDate, sipInvestment, sipRate, sipTime, sipTotalInvestment, sipTotalReturn, sipWealthGain);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        display();

        clrSIPBtn=findViewById(R.id.clear_sip);
        clrSIPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.clearSIPHistory();
                recyclerView.setVisibility(View.GONE);
                noHistory.setVisibility(View.VISIBLE);
            }
        });



    }

        public void display(){
            dbHandler.fetchSIPData();
            Cursor sipCursor= dbHandler.sipCursor;
            if (sipCursor.getCount()==0){
                try {
//                    clrSIPBtn.setVisibility(View.INVISIBLE);
                    noHistory.setVisibility(View.VISIBLE);
                }
                catch (NumberFormatException e){
                    Toast.makeText(this, "An exception occurred", Toast.LENGTH_SHORT).show();
                }


            }
            else {
                while (sipCursor.moveToNext()) {
                    // Retrieve column values using column indexes or column names
                    int investment=sipCursor.getInt(sipCursor.getColumnIndex(Params.KEY_INVESTMENT));
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
                    decimalFormat.applyPattern("#,##,###");
                    String formattedInvestment = decimalFormat.format(investment);

                    int wealthGain=sipCursor.getInt(sipCursor.getColumnIndex(Params.KEY_RETURN_AMOUNT));
                    String formattedWealth = decimalFormat.format(wealthGain);

                    int totalInvestment = sipCursor.getInt(sipCursor.getColumnIndex(Params.KEY_TOTAL_INVESTMENT));
                    String formattedTotalInvestment= decimalFormat.format(totalInvestment);

                    int totalReturn = sipCursor.getInt(sipCursor.getColumnIndex(Params.KEY_TOTAL_RETURN));
                    String formattedTotalReturn= decimalFormat.format(totalReturn);

                    sipActivityDate.add(sipCursor.getString(sipCursor.getColumnIndex(Params.KEY_SIP_ACTIVITY_DATE)));
                    sipInvestment.add(formattedInvestment);
                    sipRate.add(sipCursor.getString(sipCursor.getColumnIndex(Params.KEY_RATE)));
                    sipTime.add(sipCursor.getString(sipCursor.getColumnIndex(Params.KEY_TIME)));
                    sipTotalInvestment.add(formattedTotalInvestment);
                    sipTotalReturn.add(formattedTotalReturn);
                    sipWealthGain.add(formattedWealth);

//                    Log.d("db", "Successfully extracted");

                }
            }
            sipCursor.close();
            dbHandler.db.close();
        }

}



