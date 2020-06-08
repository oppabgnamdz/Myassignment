package com.example.myassignment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myassignment.Model.User;
import com.example.myassignment.R;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {
    private ArrayList<User> users;

    public AdapterUser(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        TextView tvUser = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvAddress = convertView.findViewById(R.id.tvAddress);
        tvUser.setText(users.get(position).getName());
        tvPhone.setText(users.get(position).getPhone());
        tvAddress.setText(users.get(position).getAddress());
        return convertView;
    }
}
