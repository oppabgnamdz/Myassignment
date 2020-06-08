package com.example.myassignment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myassignment.Model.DetailsBill;
import com.example.myassignment.R;

import java.util.ArrayList;

public class AdapterStatistic extends BaseAdapter {
    ArrayList<String> detail;

    public AdapterStatistic(ArrayList<String> detail) {
        this.detail = detail;
    }

    @Override
    public int getCount() {
        return detail.size();
    }

    @Override
    public Object getItem(int position) {
        return detail.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_statistic, parent, false);
        TextView tvShow = convertView.findViewById(R.id.tvShow);
        tvShow.setText("Sách "+ detail.get(position)+" quyển");
        return convertView;
    }
}
