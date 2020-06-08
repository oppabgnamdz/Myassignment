package com.example.myassignment.Model;

public class Bill {
    private String billName;
    private String date;

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bill(String billName, String date) {
        this.billName = billName;
        this.date = date;
    }
}
