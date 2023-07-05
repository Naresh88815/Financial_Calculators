package com.application.sipcalculator;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

public class ValueLimitFilter implements InputFilter {

    private float minValue;
    private float maxValue;


    public ValueLimitFilter(float minValue, float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            // Get the new value by combining the existing text with the newly entered text
            String newValue = dest.subSequence(0, dstart) + source.subSequence(start, end).toString() + dest.subSequence(dend, dest.length());

            // Parse the new value
            float input = Float.parseFloat(newValue);

            // Check if the new value is within the specified limits
            if (input <minValue || input > maxValue) {
                return ""; // Invalid input, so discard the entered text
            }
        }
        catch (NumberFormatException e) {
            // Error parsing the input, so discard the entered text
            return "";
        }

        // Accept the entered text
        return null;
    }

}
