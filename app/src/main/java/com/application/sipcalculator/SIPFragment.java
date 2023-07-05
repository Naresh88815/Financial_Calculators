package com.application.sipcalculator;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;


import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SIPFragment extends Fragment {
    //    private TextInputLayout investment,rate,time;
    private EditText investmentEdit, rateEdit, timeEdit;
    private Slider investmentSlider, rateSlider, timeSlider;

    private TextView totalReturnValue, totalInvestmentValue,returnAmountValue;

    private CardView outputCardView;

    private Button calculateBtn;
    private ImageFilterButton sipHistoryBtn;

    int roundedResult=0;

    int investedAmount =0;

    int returnAmt;

    long profit=0;

    Slider slider;

    View view;
    PieChart pieChart;
    List <PieEntry> pieEntryList=new ArrayList<>();

//    private void validateSlider(float value) {
//        if (value == 0) {
//            slider.setValue(1);
//        }
//    }

    private void setUpChart(){
        PieDataSet pieDataSet= new PieDataSet(pieEntryList, "");
        PieData pieData= new PieData(pieDataSet);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
       pieDataSet.setSliceSpace(2);
       pieDataSet.setValueTextSize(12);

       ArrayList<Integer>colors=new ArrayList<>();
//       colors.add(Color.GRAY);
       colors.add(ContextCompat.getColor(getContext(),R.color.Red));
       colors.add(ContextCompat.getColor(getContext(),R.color.app));


       pieDataSet.setColors(colors);

//        pieData.setValueTextSize(12f);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    private  void setValues(){
        profit=roundedResult-investedAmount;
        // Clear the existing entries in the pieEntryList
        pieEntryList.clear();

        pieEntryList.add(new PieEntry(Float.parseFloat(String.valueOf(profit)),"Total Wealth Gain"));
        pieEntryList.add(new PieEntry(Float.parseFloat(String.valueOf(investedAmount)),"Total Investment"));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sip, container, false);

        initView();
        bindEvents();
        return view;
    }

    private void bindEvents() {
        investmentSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int intValue = (int) value;
                investmentEdit.setText(String.valueOf(intValue));
            }

        });


        rateSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedValue = df.format(value);
                rateEdit.setText(formattedValue);
            }
        });

        timeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int intValue=(int) value;
                timeEdit.setText(String.valueOf(intValue));
            }
        });



        investmentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    float value = Float.parseFloat(s.toString());
//                    investmentSlider.setValue(value);
//                }

                // Set the cursor position to the end of the text
                investmentEdit.setSelection(investmentEdit.getText().length());
                }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int after, int count) {
                String inputText = s.toString();

                if (inputText != null && !inputText.isEmpty() && inputText!="0"){
                        try {
                            // Create the value limit filter with desired limits
                            ValueLimitFilter valueLimitFilter = new ValueLimitFilter(1, 100000);

                            // Set the value limit filter as the filter for the EditText
                            investmentEdit.setFilters(new InputFilter[]{valueLimitFilter});

                            float investmentValue = Float.parseFloat(String.valueOf(s));
                            investmentSlider.setValue(investmentValue);

                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();
                        }
                    }
                else{
                    investmentSlider.setValue(1);
                }
                }

        });


        rateEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        rateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (inputText != null && !inputText.isEmpty()) {
                    try {

                        // Create the value limit filter with desired limits
                        ValueLimitFilter valueLimitFilter = new ValueLimitFilter(0f, 30f);

                        // Set the value limit filter as the filter for the EditText
                        rateEdit.setFilters(new InputFilter[]{valueLimitFilter});


                        float rateValue = Float.parseFloat(String.valueOf(s));
                        rateSlider.setValue(rateValue);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    float value = Float.parseFloat(s.toString());
//                    rateSlider.setValue(value);
//                }

                // Set the cursor position to the end of the text
                rateEdit.setSelection(rateEdit.getText().length());
            }
        });


        timeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (inputText != null && !inputText.isEmpty() && inputText!="0") {
                    try {
                        // Create the value limit filter with desired limits
                        ValueLimitFilter valueLimitFilter = new ValueLimitFilter(1, 600);

                        // Set the value limit filter as the filter for the EditText
                        timeEdit.setFilters(new InputFilter[]{valueLimitFilter});

                        float timeValue = Float.parseFloat(s.toString());
                        timeSlider.setValue(timeValue);

                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    timeSlider.setValue(1);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    float value = Float.parseFloat(s.toString());
//                    timeSlider.setValue(value);
//                }

                // Set the cursor position to the end of the text
                timeEdit.setSelection(timeEdit.getText().length());
            }
        });


        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1=String.valueOf(investmentEdit);
                String input2=String.valueOf(rateEdit);
                String input3=String.valueOf(timeEdit);



                if (input1!=null && !input1.isEmpty() && input2!=null && !input2.isEmpty() && input3!=null && !input3.isEmpty() ) {

                    try {

                        int investment = Integer.parseInt(investmentEdit.getText().toString());
                        double rate = Double.parseDouble(rateEdit.getText().toString());
                        int numberOfPeriods = Integer.parseInt(timeEdit.getText().toString());

                        double monthlyRate = rate / 100 / 12.0;
//                        int numberOfPeriods = (int) (12 * timeInYears);

                        double result = investment * (Math.pow(1 + monthlyRate, numberOfPeriods) - 1) * (1 + monthlyRate) / monthlyRate;


                        DecimalFormat df = new DecimalFormat("#.##");
                        String formattedResult = df.format(result);

                        // Convert formattedResult to a rounded value
                        double roundedValue = Double.parseDouble(formattedResult);

                        // Round off the roundedValue to the nearest whole number
                        roundedResult = (int) Math.round(roundedValue);

                        int investedAmt = investment * numberOfPeriods;
                        DecimalFormat iv = new DecimalFormat("#.##");
                        investedAmount= Integer.parseInt(iv.format(investedAmt));

                        returnAmt= roundedResult-investedAmt;

                        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
                        decimalFormat.applyPattern("#,##,###");


                        String formattedTotalReturn = decimalFormat.format(roundedResult);
                        String  formattedTotalInvestment=decimalFormat.format(investedAmount);
                        String formattedReturnAmt=decimalFormat.format(returnAmt);

                        totalReturnValue.setText(formattedTotalReturn);
                        totalInvestmentValue.setText(formattedTotalInvestment);
                        returnAmountValue.setText(formattedReturnAmt);

                        outputCardView.setVisibility(View.VISIBLE);
                        // Create a SimpleDateFormat object with the desired format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM, yyyy h:mm", Locale.getDefault());

                        // Get the current date and time
                        Date currentDate = new Date();

                        // Format the date and time using the SimpleDateFormat object
                        String formattedDate = simpleDateFormat.format(currentDate);
                        setValues();
                        setUpChart();

                        DBHandler dbHandler=new DBHandler(getContext());
                        SIPData sipData= new SIPData();
                        sipData.setSipInvestment(String.valueOf(investment));
                        sipData.setSipRate(String.valueOf(rate));
                        sipData.setSipTime(String.valueOf(numberOfPeriods));
                        sipData.setSipActivityDate(formattedDate);
                        sipData.setSipTotalInvestment(String.valueOf(investedAmount));
                        sipData.setSipTotalReturn(String.valueOf(roundedResult));
                        sipData.setSipReturnAmount(String.valueOf(returnAmt));

                        dbHandler.addSIPHistory(sipData);


                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Please enter values in all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        sipHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the new activity after the delay
                        Intent intent = new Intent(getContext(), HistorySIP.class);
                        startActivity(intent);
                    }
                }, 500);
            }
        });
    }

    private void initView() {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_sip, container, false);
        investmentEdit = view.findViewById(R.id.investmentEdit);
//        investmentEdit.setText("0");

        investmentSlider = view.findViewById(R.id.investmentSlider);
//        investmentSlider.setValue(1);

        rateEdit = view.findViewById(R.id.rateEdit);

        rateSlider = view.findViewById(R.id.rateSlider);

        timeEdit = view.findViewById(R.id.timeEdit);

        timeSlider = view.findViewById(R.id.timeSlider);

        calculateBtn = view.findViewById(R.id.calculateBtn);

        totalReturnValue = view.findViewById(R.id.totalReturnValue);

        totalInvestmentValue = view.findViewById(R.id.totalInvestmentValue);

        pieChart = view.findViewById(R.id.chart);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getDescription().setText("");
        pieChart.setNoDataText("");

        outputCardView= view.findViewById(R.id.output);
        outputCardView.setVisibility(view.INVISIBLE);

        returnAmountValue=view.findViewById(R.id.returnAmountValue);
        sipHistoryBtn=view.findViewById(R.id.sipHistoryBtn);



    }



}