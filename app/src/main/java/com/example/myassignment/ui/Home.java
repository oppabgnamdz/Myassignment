package com.example.myassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myassignment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    ImageView imageUser, imageCategory, imageBook, imageBill, imageBestSale, imageSatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        imageUser = view.findViewById(R.id.imageUser);
        imageCategory = view.findViewById(R.id.imageCategory);
        imageBook = view.findViewById(R.id.imageBook);
        imageBill = view.findViewById(R.id.imageBill);
        imageBestSale = view.findViewById(R.id.imageBestSale);
        imageSatistic = view.findViewById(R.id.imageSatistic);
        handling();
        return view;
    }

    public void handling() {
        imageUser.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_user));
        imageCategory.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_category));
        imageSatistic.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_statistic));
        imageBook.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_book));
        imageBestSale.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_best_sale));
        imageBill.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_bill));
    }
}
