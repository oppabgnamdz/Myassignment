package com.example.myassignment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myassignment.Model.Bill;
import com.example.myassignment.R;

import java.util.ArrayList;

public class AdapterBill extends BaseAdapter {
    ArrayList<Bill> bills;

    public AdapterBill(ArrayList<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return bills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bill, parent, false);
        TextView tvBillName = convertView.findViewById(R.id.tvBillName);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        tvBillName.setText(bills.get(position).getBillName());
        tvDate.setText(bills.get(position).getDate());
        return convertView;
    }
}
