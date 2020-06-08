package com.example.myassignment.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.myassignment.Model.DetailsBill;

import java.util.ArrayList;

public class AdapterStatistic extends BaseAdapter {
    ArrayList<DetailsBill> detailsBills;

    public AdapterStatistic(ArrayList<DetailsBill> detailsBills) {
        this.detailsBills = detailsBills;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
