package com.example.myassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myassignment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BestSale#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BestSale extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_best_sale, container, false);
    }
}
