package com.application.sipcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HistorySIP extends Fragment {
    private TextView sipActivityDate, sipInvestment, sipRate, sipTime, sipTotalInvestment, sipTotalReturn, sipWealthGain;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view= inflater.inflate(R.layout.history_sip, container, false);
        initView();
        setSIPValues();
        return  view;
    }
    public  void initView(){
        sipActivityDate=view.findViewById(R.id.sipActivityDate);
        sipInvestment=view.findViewById(R.id.sipInvestment);
        sipRate=view.findViewById(R.id.sipRate);
        sipTime=view.findViewById(R.id.sipTime);
        sipTotalInvestment=view.findViewById(R.id.sipTotalInvestment);
        sipTotalReturn=view.findViewById(R.id.sipTotalReturn);
        sipWealthGain=view.findViewById(R.id.sipWealthGain);
    }
    public void setSIPValues(){
        DBHandler dbHandler = new DBHandler(getContext());
        sipActivityDate.setText(dbHandler.sipActivityDate);
        sipInvestment.setText(dbHandler.investment);
        sipRate.setText(dbHandler.rate);
        sipTime.setText(dbHandler.time);
        sipTotalInvestment.setText(dbHandler.totalInvestment);
        sipTotalReturn.setText(dbHandler.totalReturn);
        sipWealthGain.setText(dbHandler.returnAmount);

    }
}
