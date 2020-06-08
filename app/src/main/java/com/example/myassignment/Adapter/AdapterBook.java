package com.example.myassignment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myassignment.Model.Book;
import com.example.myassignment.R;

import java.util.ArrayList;

public class AdapterBook extends BaseAdapter {
    ArrayList<com.example.myassignment.Model.Book> books;

    public AdapterBook(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book, parent, false);
        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvCategoryName = convertView.findViewById(R.id.tvCategoryName);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvMoney = convertView.findViewById(R.id.tvMoney);
        TextView tvNumber = convertView.findViewById(R.id.tvNumber);
        tvBookName.setText(books.get(position).getBookName());
        tvCategoryName.setText(books.get(position).getCategoryName());
        tvTitle.setText(books.get(position).getTitle());
        tvAuthor.setText(books.get(position).getAuthor());
        tvDate.setText(books.get(position).getDate());
        tvMoney.setText(String.valueOf(books.get(position).getMoney()));
        tvNumber.setText(String.valueOf(books.get(position).getNumber()));
        return convertView;
    }
}
