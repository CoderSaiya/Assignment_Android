package com.example.moneymanage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanage.Entity.Transaction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home extends AppCompatActivity {
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Adapter adapter;
    private BarChart barChart;
    private ListView listView;
    private TextView txtBalance;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

//        transactions.add(new Transaction("Thu","Lương tháng 8/2024",1000000, new Date(2024, 8, 1)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 2)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 3)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 4)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 5)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 6)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 7)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 8)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 9)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 10)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 11)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 12)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 13)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 14)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 15)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 16)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 17)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 18)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 19)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 20)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 21)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 7, 22)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 8, 1)));
//        transactions.add(new Transaction("Chi","Đi chợ",200000, new Date(2024, 8, 2)));
//
//
//        listView = findViewById(R.id.list);
//        adapter = new Adapter(this, transactions);
//        listView.setAdapter(adapter);

//        int totalHeight = 0;
//        for (int i = 0; i < adapter.getCount(); i++) {
//            View listItem = adapter.getView(i, null, listView);
//            listItem.measure(
//                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//
//        TextView txtBalance = findViewById(R.id.txtBalance);
//        float totalSum = 0;
//        for(Transaction transaction : transactions){
//            if(transaction.getCategory().equals("Thu")){
//                totalSum+= transaction.getAmount();
//            }else{
//                totalSum-= transaction.getAmount();
//            }
//
//        }
//        txtBalance.setText(String.format("%,.0f", totalSum));
//
//        barChart = findViewById(R.id.barChart);
//        setupBarChart();

        listView = findViewById(R.id.list);
        txtBalance = findViewById(R.id.txtBalance);
        barChart = findViewById(R.id.barChart);
        phone = getIntent().getStringExtra("phone");

        new LoadTransactionsTask().execute();
    }

    private class LoadTransactionsTask extends AsyncTask<Void, Void, ArrayList<Transaction>> {
        @Override
        protected ArrayList<Transaction> doInBackground(Void... voids) {
            ArrayList<Transaction> transactions = new ArrayList<>();

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                // Kết nối đến MySQL database
                String DB_URL = "jdbc:mysql://10.0.2.2:3306/moneymanage?connectTimeout=10000&socketTimeout=10000&useSSL=false";

                String USER = "root";
                String PASS = "NhatCuong04@";

                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                if (conn != null) {
                    String query = "SELECT * FROM transactions A JOIN users B ON B.id = A.user_id WHERE B.phone = '" + phone + "'";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        String category = rs.getString("A.category");
                        String description = rs.getString("A.description");
                        float amount = rs.getFloat("A.amount");
                        Date date = rs.getDate("A.date");
                        transactions.add(new Transaction(category, description, amount, date));
                    }
                } else {
                    Log.e("DB_ERROR", "Không thể kết nối tới cơ sở dữ liệu.");
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi kết nối cơ sở dữ liệu hoặc truy vấn", e);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (Exception e) {
                    Log.e("DB_ERROR", "Lỗi khi đóng kết nối", e);
                }
            }

            return transactions;
        }

        @Override
        protected void onPostExecute(ArrayList<Transaction> transactionsResult) {
            super.onPostExecute(transactionsResult);

            if (transactionsResult.isEmpty()) {
//                Toast.makeText(Home.this, "Không tải được dữ liệu!", Toast.LENGTH_SHORT).show();
            } else {
                transactions = transactionsResult;

                // Cập nhật ListView
                adapter = new Adapter(Home.this, transactions);
                listView.setAdapter(adapter);

                // Tính toán chiều cao cho ListView
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

                // Cập nhật số dư
                float totalSum = 0;
                for (Transaction transaction : transactions) {
                    if (transaction.getCategory().equals("Thu")) {
                        totalSum += transaction.getAmount();
                    } else {
                        totalSum -= transaction.getAmount();
                    }
                }
                txtBalance.setText(String.format("%,.0f", totalSum));

                // Thiết lập biểu đồ
                setupBarChart();
            }
        }
    }

    private void setupBarChart() {
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        ArrayList<BarEntry> expensesEntries = new ArrayList<>();

        // Prepare data entries for the bar chart
        float incomeTotal = 0f, expensesTotal = 0f;
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getCategory().equals("Thu")) {
                incomeTotal += transaction.getAmount();
            } else if (transaction.getCategory().equals("Chi")) {
                expensesTotal += transaction.getAmount();
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