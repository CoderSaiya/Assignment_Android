package com.example.moneymanage.Entity;

import java.util.Date;

public class Transaction {
    private String category;
    private String description;
    private  double amount;
    private Date date;
    public Transaction(String category, String description, double amount, Date date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
}
