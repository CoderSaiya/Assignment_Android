package com.example.moneymanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moneymanage.Entity.Finance;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Home extends AppCompatActivity {
    private ArrayList<Finance> finances = new ArrayList<>();
    private Adapter adapter;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        finances.add(new Finance("Thu","Lương tháng 8/2024",1000000, new Date(2024, 8, 1)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 2)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 3)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 4)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 5)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 6)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 7)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 8)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 9)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 10)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 11)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 12)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 13)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 14)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 15)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 16)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 17)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 18)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 19)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 20)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 21)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 7, 22)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 8, 1)));
        finances.add(new Finance("Chi","Đi chợ",200000, new Date(2024, 8, 2)));

        ListView listView = findViewById(R.id.list);
        adapter = new Adapter(this, finances);
        listView.setAdapter(adapter);

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

        TextView txtBalance = findViewById(R.id.txtBalance);
        float totalSum = 0;
        for(Finance finance : finances){
            if(finance.getCategory().equals("Thu")){
                totalSum+=finance.getValue();
            }else{
                totalSum-=finance.getValue();
            }

        }
        txtBalance.setText(String.format("%,.0f", totalSum));

        barChart = findViewById(R.id.barChart);
        setupBarChart();
    }

    private void setupBarChart() {
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        ArrayList<BarEntry> expensesEntries = new ArrayList<>();

        // Prepare data entries for the bar chart
        float incomeTotal = 0f, expensesTotal = 0f;
        for (int i = 0; i < finances.size(); i++) {
            Finance finance = finances.get(i);
            if (finance.getCategory().equals("Thu")) {
                incomeTotal += finance.getValue();
            } else if (finance.getCategory().equals("Chi")) {
                expensesTotal += finance.getValue();
            }
        }
        incomeEntries.add(new BarEntry(0, incomeTotal));
        expensesEntries.add(new BarEntry(1, expensesTotal));

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Thu");
        incomeDataSet.setColor(Color.GREEN);
        BarDataSet expensesDataSet = new BarDataSet(expensesEntries, "Chi");
        expensesDataSet.setColor(Color.RED);

        BarData barData = new BarData(incomeDataSet, expensesDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Thu", "Chi"}));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);
        barChart.getAxisRight().setEnabled(false);

        barChart.getBarData().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.0f", value);
            }
        });
        barChart.getBarData().setValueTextSize(16f);

        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);

        barChart.invalidate();
    }
}