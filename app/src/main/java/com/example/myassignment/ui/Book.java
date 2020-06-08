package com.example.myassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myassignment.Adapter.AdapterBook;
import com.example.myassignment.Database;
import com.example.myassignment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Book#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Book extends Fragment {
    Database database;
    ListView lvListBook;
    AdapterBook adapterBook;
    ArrayList<com.example.myassignment.Model.Book> books;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        lvListBook = view.findViewById(R.id.lvListBook);
        database = new Database(getContext());
        books = database.getAllBook();
        adapterBook = new AdapterBook(books);
        lvListBook.setAdapter(adapterBook);
        return view;

    }
}
