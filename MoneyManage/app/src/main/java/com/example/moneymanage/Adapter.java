package com.example.moneymanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.moneymanage.Entity.Finance;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {
    private Context context;
    private List<Finance> originalList;
    private List<Finance>filterdList;
    private LayoutInflater inflater;
    public Adapter(Context context, ArrayList<Finance> finances){
        this.context = context;
        originalList = new ArrayList<>(finances);
        filterdList = new ArrayList<>(finances);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filterdList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView category = convertView.findViewById(R.id.category);
        TextView value = convertView.findViewById(R.id.value);
        TextView date = convertView.findViewById(R.id.date);

        Finance finance = (Finance) getItem(position);
        title.setText(finance.getTitle());
        category.setText(finance.getCategory());
        date.setText(String.valueOf(finance.getDate()));

        if (finance.getCategory().equals("Thu")) {
            value.setText(String.format("+%,.0f", finance.getValue()));
            value.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else if (finance.getCategory().equals("Chi")) {
            value.setText(String.format("-%,.0f", finance.getValue()));
            value.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        }

        return convertView;
    }
}
