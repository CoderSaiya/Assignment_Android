package com.example.moneymanage.Entity;

import java.util.Date;

public class Finance {
    private String category;
    private String title;
    private  double value;
    private Date date;
    public Finance(String category, String title, double value, Date date) {
        this.category = category;
        this.title = title;
        this.value = value;
        this.date = date;
    }
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
}
