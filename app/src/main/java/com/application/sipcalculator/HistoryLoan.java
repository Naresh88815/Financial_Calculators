package com.application.sipcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HistoryLoan extends Fragment {
    private TextView loanActivityDate, loanAmount, loanRate, loanTime, loanEMI, loanInterestPayable, loanTotalAmount;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view= inflater.inflate(R.layout.history_sip, container, false);
        initView();
        setLoanValues();
        return  view;
    }

    public  void initView(){
        loanActivityDate=view.findViewById(R.id.loanActivityDate);
        loanAmount=view.findViewById(R.id.loanAmount);
        loanRate=view.findViewById(R.id.loanRate);
        loanTime=view.findViewById(R.id.loanTime);
        loanEMI=view.findViewById(R.id.loanEMI);
        loanInterestPayable=view.findViewById(R.id.loanInterestPayable);
        loanTotalAmount=view.findViewById(R.id.loanTotalAmount);
    }
    public void setLoanValues(){
        DBHandler dbHandler = new DBHandler(getContext());
        loanActivityDate.setText(dbHandler.sipActivityDate);
        loanAmount.setText(dbHandler.loanAmount);
        loanRate.setText(dbHandler.loanRate);
        loanTime.setText(dbHandler.loanTime);
        loanEMI.setText(dbHandler.loanEMI);
        loanInterestPayable.setText(dbHandler.loanInterestPayable);
        loanTotalAmount.setText(dbHandler.loanTotalAmount);

    }
}
