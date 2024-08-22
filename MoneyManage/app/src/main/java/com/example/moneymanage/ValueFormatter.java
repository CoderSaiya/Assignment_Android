package com.example.moneymanage;

import java.text.DecimalFormat;

public class ValueFormatter extends com.github.mikephil.charting.formatter.ValueFormatter {
    private DecimalFormat decimalFormat;

    public ValueFormatter() {
        decimalFormat = new DecimalFormat("#,###");
    }

    @Override
    public String getFormattedValue(float value) {
        return decimalFormat.format(value);
    }
}
