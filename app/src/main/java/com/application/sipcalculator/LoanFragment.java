package com.application.sipcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

public class LoanFragment extends Fragment {
    private TextView emiValue, totalInterestValue,totalAmountValue;
    private EditText loanEdit, interestRateEdit, loanTimeEdit;
    private Slider loanSlider, interestRateSlider, loanTimeSlider;
    Button loanCalculateBtn;
    ImageFilterButton loanHistoryBtn;

    private CardView loanOutput;
    int roundedEMIResult=0;

    double totalAmt;

    int roundedTotalAmt=0;

    String formattedInterestAmount="0.0";

    int roundedInterestResult=0;

    double interestAmount=0;

    int loan;

    View view;

    private CardView loanOutputCardView;

    PieChart loanPieChart;
    List<PieEntry> pieEntryList=new ArrayList<>();

    private void setUpChart(){
        PieDataSet pieDataSet= new PieDataSet(pieEntryList, "");
        PieData pieData= new PieData(pieDataSet);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer>colors=new ArrayList<>();
//       colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(ContextCompat.getColor(getContext(),R.color.app));


        pieDataSet.setColors(colors);

//        pieData.setValueTextSize(12f);

        loanPieChart.setData(pieData);
        loanPieChart.invalidate();
    }
    private  void setValues(){
//        profit=roundedResult-Integer.parseInt(investedAmount);
        // Clear the existing entries in the pieEntryList
        pieEntryList.clear();

        pieEntryList.add(new PieEntry(Float.parseFloat(String.valueOf(roundedInterestResult)),"Interest Payable"));
        pieEntryList.add(new PieEntry(Float.parseFloat(String.valueOf(loan)),"Loan Amount"));
    }


    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState){
        view= inflater.inflate(R.layout.fragment_loan, container, false);
        initView();
        bindEvents();
        return  view;
    }

    private void initView(){
        emiValue=view.findViewById(R.id.emiValue);

        loanEdit=view.findViewById(R.id.loanEdit);

        interestRateEdit=view.findViewById(R.id.interestRateEdit);

        loanTimeEdit=view.findViewById(R.id.loanTimeEdit);

        loanSlider=view.findViewById(R.id.loanSlider);

        interestRateSlider=view.findViewById(R.id.interestRateSlider);

        loanTimeSlider=view.findViewById(R.id.loanTimeSlider);

        loanCalculateBtn=view.findViewById(R.id.loanCalculateBtn);

        loanOutputCardView=view.findViewById(R.id.loanOutput);
        loanOutputCardView.setVisibility(view.INVISIBLE);

        totalInterestValue=view.findViewById(R.id.totalInterestValue);

        totalAmountValue=view.findViewById(R.id.totalAmountValue);

        loanPieChart=view.findViewById(R.id.loanPieChart);
        loanPieChart.setDrawHoleEnabled(false);
        loanPieChart.getDescription().setText("");
        loanPieChart.setNoDataText("");

        loanHistoryBtn=view.findViewById(R.id.loanHistoryBtn);


    }

    private void bindEvents() {
        loanSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int intValue = (int) value;
                loanEdit.setText(String.valueOf(intValue));
            }

        });


        interestRateSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedValue = df.format(value);
                interestRateEdit.setText(formattedValue);
            }
        });

        loanTimeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int intValue=(int) value;
                loanTimeEdit.setText(String.valueOf(intValue));
            }
        });



        loanEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    float value = Float.parseFloat(s.toString());
                    loanSlider.setValue(value);
                }

                // Set the cursor position to the end of the text
                loanEdit.setSelection(loanEdit.getText().length());
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int after, int count) {
                String inputText = s.toString();
                if (inputText != null && !inputText.isEmpty()) {
                    try {
                        // Create the value limit filter with desired limits
                        ValueLimitFilter valueLimitFilter = new ValueLimitFilter(1, 5000000);

                        // Set the value limit filter as the filter for the EditText
                        loanEdit.setFilters(new InputFilter[]{valueLimitFilter});

                        float investmentValue = Float.parseFloat(String.valueOf(s));
                        loanSlider.setValue(investmentValue);

                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });


        interestRateEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        interestRateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (inputText != null && !inputText.isEmpty()) {
                    try {
                        // Create the value limit filter with desired limits
                        ValueLimitFilter valueLimitFilter = new ValueLimitFilter(1, 30);

                        // Set the value limit filter as the filter for the EditText
                        interestRateEdit.setFilters(new InputFilter[]{valueLimitFilter});

                        float rateValue = Float.parseFloat(String.valueOf(s));
                        interestRateSlider.setValue(rateValue);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    float value = Float.parseFloat(s.toString());
                    interestRateSlider.setValue(value);
                }

                // Set the cursor position to the end of the text
                interestRateEdit.setSelection(interestRateEdit.getText().length());
            }
        });


        loanTimeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (inputText != null && !inputText.isEmpty()) {
                    try {
                        // Create the value limit filter with desired limits
                        ValueLimitFilter valueLimitFilter = new ValueLimitFilter(1, 600);

                        // Set the value limit filter as the filter for the EditText
                        loanTimeEdit.setFilters(new InputFilter[]{valueLimitFilter});

                        float timeValue = Float.parseFloat(s.toString());
                        loanTimeSlider.setValue(timeValue);

                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    float value = Float.parseFloat(s.toString());
                    loanTimeSlider.setValue(value);
                }

                // Set the cursor position to the end of the text
                loanTimeEdit.setSelection(loanTimeEdit.getText().length());
            }
        });


        loanCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1=String.valueOf(loanEdit);
                String input2=String.valueOf(interestRateEdit);
                String input3=String.valueOf(loanTimeEdit);

                if (input1!=null && !input1.isEmpty() && input2!=null && !input2.isEmpty() && input3!=null && !input3.isEmpty() ) {

                    try {
                        loan = Integer.parseInt(loanEdit.getText().toString());
                        double interestRate = Double.parseDouble(interestRateEdit.getText().toString());
                        int numberOfInstallments = Integer.parseInt(loanTimeEdit.getText().toString());

                        double monthlyInterestRate = interestRate / 100 / 12.0;

                        double emi = (loan * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfInstallments))/(Math.pow(1 + monthlyInterestRate, numberOfInstallments) - 1);


                        DecimalFormat edf = new DecimalFormat("#.##");
                        String emiFormattedResult = edf.format(emi);

                        // Convert emiFormattedResult to a rounded value
                        double emiRoundedValue = Double.parseDouble(emiFormattedResult);

                        // Round off the roundedValue to the nearest whole number
                        roundedEMIResult = (int) Math.round(emiRoundedValue);

                        totalAmt = emi * numberOfInstallments;
                        interestAmount=totalAmt-loan;

                        DecimalFormat tdf = new DecimalFormat("#.##");
                        formattedInterestAmount=tdf.format(interestAmount);

                        double interestRoundedValue = Double.parseDouble(formattedInterestAmount);
                        roundedInterestResult= (int) Math.round(interestRoundedValue);

                        roundedTotalAmt= (int) Math.round(totalAmt);

                        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
                        decimalFormat.applyPattern("#,##,###");

                        String formattedEmiResult = decimalFormat.format(roundedEMIResult);
                        String formattedInterestResult = decimalFormat.format(roundedInterestResult);
                        String formattedTotalAmt = decimalFormat.format(roundedTotalAmt);

                        emiValue.setText(formattedEmiResult);
                        totalInterestValue.setText(formattedInterestResult);
                        totalAmountValue.setText(formattedTotalAmt);


                        loanOutputCardView.setVisibility(View.VISIBLE);

                        // Create a SimpleDateFormat object with the desired format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm", Locale.getDefault());

                        // Get the current date and time
                        Date currentDate = new Date();

                        // Format the date and time using the SimpleDateFormat object
                        String formattedDate = simpleDateFormat.format(currentDate);



                        setValues();
                        setUpChart();

                        DBHandler dbHandler= new DBHandler(getContext());
                        LoanData loanData = new LoanData();
                        loanData.setLoanActivityDate(formattedDate);
                        loanData.setLoanAmount(String.valueOf(loan));
                        loanData.setLoanRate(String.valueOf(interestRate));
                        loanData.setLoanTime(String.valueOf(numberOfInstallments));
                        loanData.setLoanEMI(String.valueOf(roundedEMIResult));
                        loanData.setLoanInterestPayable(String.valueOf(roundedInterestResult));
                        loanData.setLoanTotalAmount(String.valueOf(roundedTotalAmt));

                        dbHandler.addLoanHistory(loanData);
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Please enter values in all the fields", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        loanHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the new activity after the delay
                        Intent intent = new Intent(getContext(), HistoryLoan.class);
                        startActivity(intent);
                    }
                }, 500);
            }
        });
    }
}

