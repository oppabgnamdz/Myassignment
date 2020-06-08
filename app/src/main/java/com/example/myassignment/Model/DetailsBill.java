package com.example.myassignment.Model;

public class DetailsBill {
    private String billName;
    private String bookName;
    private String numberSale;

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getNumberSale() {
        return numberSale;
    }

    public void setNumberSale(String numberSale) {
        this.numberSale = numberSale;
    }

    public DetailsBill(String billName, String bookName, String numberSale) {
        this.billName = billName;
        this.bookName = bookName;
        this.numberSale = numberSale;
    }
}
