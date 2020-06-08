package com.example.myassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myassignment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statistic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statistic extends Fragment {
    ListView lvListStatistic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        EditText edtMonth = view.findViewById(R.id.edtMonth);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);
        lvListStatistic= view.findViewById(R.id.lvListStatistic);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
