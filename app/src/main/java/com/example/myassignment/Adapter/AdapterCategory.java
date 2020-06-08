package com.example.myassignment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myassignment.Model.Category;
import com.example.myassignment.R;

import java.util.ArrayList;

public class AdapterCategory extends BaseAdapter {
    ArrayList<Category> categories;

    public AdapterCategory(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        TextView tvPosition = convertView.findViewById(R.id.tvPosition);
        tvName.setText(categories.get(position).getName());
        tvDescription.setText(categories.get(position).getDescription());
        tvPosition.setText(categories.get(position).getPosition());
        return convertView;
    }
}
