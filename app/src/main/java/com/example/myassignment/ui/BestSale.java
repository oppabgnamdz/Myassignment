package com.example.myassignment.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myassignment.Adapter.AdapterBestSale;
import com.example.myassignment.Adapter.AdapterStatistic;
import com.example.myassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BestSale#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BestSale extends Fragment {
    FirebaseFirestore db;
    ListView lvListBestSale;
    ArrayList<String> bookName;
    ArrayList<String> moneyBook;
    AdapterBestSale adapterBestSale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_best_sale, container, false);
        final EditText edtDay = view.findViewById(R.id.edtDay);
        final EditText edtMonth = view.findViewById(R.id.edtMonth);
        Button btnComfirm = view.findViewById(R.id.btnConfirm);
        lvListBestSale = view.findViewById(R.id.lvListBestSale);
        db = FirebaseFirestore.getInstance();
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = new ArrayList<>();
                moneyBook = new ArrayList<>();
                final ArrayList<String> billName = new ArrayList<>();
                if (edtDay.getText().toString().length() == 0) {
                    if (edtMonth.getText().toString().length() > 0) {
                        db.collection("bill").whereEqualTo("month", Integer.parseInt(edtMonth.getText().toString())).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<QuerySnapshot> task1) {
                                        Log.e("data", task1.getResult().size() + "");
                                        if (task1.getResult().size() > 0) {
                                            for (QueryDocumentSnapshot documentSnapshot : task1.getResult()) {
                                                billName.add((String) documentSnapshot.getData().get("billName"));
                                            }
                                            Log.e("billname", billName + "");

                                            db.collection("book").get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                            if (task2.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task2.getResult()) {
                                                                    bookName.add((String) document.getData().get("bookName"));
                                                                    moneyBook.add((String) document.getData().get("money"));

                                                                }
                                                                Log.e("data", bookName + "");
                                                                final ArrayList<String> statisticBookName = new ArrayList<>();
                                                                final ArrayList<String> staticticNumber = new ArrayList<>();
                                                                final ArrayList<String> staticticPrice = new ArrayList<>();
                                                                for (int i = 0; i < billName.size(); i++) {
                                                                    db.collection("statistic").whereEqualTo("billName", billName.get(i)).get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                                                                    if (task3.isSuccessful() && task3.getResult().size() > 0) {
                                                                                        for (QueryDocumentSnapshot document : task3.getResult()) {
                                                                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                                                                            statisticBookName.add((String) document.getData().get("bookName"));
                                                                                            staticticNumber.add((String) document.getData().get("number"));
                                                                                        }
                                                                                        Log.e("dataBook", statisticBookName + "");
                                                                                        Log.e("dataNumber", staticticNumber + "");
                                                                                        HashMap<String, Integer> map = new HashMap<>();
                                                                                        for (int i = 0; i < bookName.size(); i++) {
                                                                                            int total = 0;
                                                                                            for (int j = 0; j < staticticNumber.size(); j++) {
                                                                                                if (bookName.get(i).equals(statisticBookName.get(j))) {
                                                                                                    total += Integer.parseInt(staticticNumber.get(j));
                                                                                                }
                                                                                            }
                                                                                            map.put(bookName.get(i), total * Integer.valueOf(moneyBook.get(i)));

                                                                                        }
                                                                                        HashMap<String, Integer> mapFinal = sortByValue(map);
                                                                                        ArrayList<String> show = new ArrayList<>();
                                                                                        for (int j = 0; j < mapFinal.size(); j++) {
                                                                                            show.add(mapFinal.entrySet().toArray()[j].toString());
                                                                                        }
                                                                                        Log.e("nenenenene", mapFinal + "");
                                                                                        Log.e("map ne", show + "");
                                                                                        adapterBestSale = new AdapterBestSale(show);
                                                                                        lvListBestSale.setAdapter(adapterBestSale);

                                                                                    } else {
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            } else {
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(getContext(), "Tháng này không có dữ liệu", Toast.LENGTH_SHORT).show();
                                            ArrayList<String> show = new ArrayList<>();
                                            adapterBestSale = new AdapterBestSale(show);
                                            lvListBestSale.setAdapter(adapterBestSale);
                                        }
//                                }
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "Bạn chưa nhập gì", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (edtMonth.getText().toString().length() > 0) {
                        db.collection("bill")
                                .whereEqualTo("month", Integer.parseInt(edtMonth.getText().toString()))
                                .whereEqualTo("day", Integer.parseInt(edtDay.getText().toString()))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<QuerySnapshot> task1) {
                                        Log.e("data", task1.getResult().size() + "");
                                        Log.e("edtDay", edtDay.getText().toString() + "");
                                        if (task1.getResult().size() > 0) {
                                            for (QueryDocumentSnapshot documentSnapshot : task1.getResult()) {
                                                billName.add((String) documentSnapshot.getData().get("billName"));
                                            }
                                            Log.e("billname", billName + "");

                                            db.collection("book").get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                            if (task2.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task2.getResult()) {
                                                                    bookName.add((String) document.getData().get("bookName"));
                                                                    moneyBook.add((String) document.getData().get("money"));

                                                                }
                                                                Log.e("data", bookName + "");
                                                                final ArrayList<String> statisticBookName = new ArrayList<>();
                                                                final ArrayList<String> staticticNumber = new ArrayList<>();
                                                                final ArrayList<String> staticticPrice = new ArrayList<>();
                                                                for (int i = 0; i < billName.size(); i++) {
                                                                    db.collection("statistic").whereEqualTo("billName", billName.get(i)).get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                                                                    if (task3.isSuccessful() && task3.getResult().size() > 0) {
                                                                                        for (QueryDocumentSnapshot document : task3.getResult()) {
                                                                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                                                                            statisticBookName.add((String) document.getData().get("bookName"));
                                                                                            staticticNumber.add((String) document.getData().get("number"));
                                                                                        }
                                                                                        Log.e("dataBook", statisticBookName + "");
                                                                                        Log.e("dataNumber", staticticNumber + "");
                                                                                        HashMap<String, Integer> map = new HashMap<>();
                                                                                        for (int i = 0; i < bookName.size(); i++) {
                                                                                            int total = 0;
                                                                                            for (int j = 0; j < staticticNumber.size(); j++) {
                                                                                                if (bookName.get(i).equals(statisticBookName.get(j))) {
                                                                                                    total += Integer.parseInt(staticticNumber.get(j));
                                                                                                }
                                                                                            }
                                                                                            map.put(bookName.get(i), total * Integer.valueOf(moneyBook.get(i)));

                                                                                        }
                                                                                        HashMap<String, Integer> mapFinal = sortByValue(map);
                                                                                        ArrayList<String> show = new ArrayList<>();
                                                                                        for (int j = 0; j < mapFinal.size(); j++) {
                                                                                            show.add(mapFinal.entrySet().toArray()[j].toString());
                                                                                        }
                                                                                        Log.e("nenenenene", mapFinal + "");
                                                                                        Log.e("map ne", show + "");
                                                                                        adapterBestSale = new AdapterBestSale(show);
                                                                                        lvListBestSale.setAdapter(adapterBestSale);

                                                                                    } else {
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            } else {
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(getContext(), "Tháng này không có dữ liệu", Toast.LENGTH_SHORT).show();
                                            ArrayList<String> show = new ArrayList<>();
                                            adapterBestSale = new AdapterBestSale(show);
                                            lvListBestSale.setAdapter(adapterBestSale);
                                        }
//                                }
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "Bạn chưa nhập gì", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
