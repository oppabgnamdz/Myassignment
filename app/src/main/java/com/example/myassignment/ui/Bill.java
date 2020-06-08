package com.example.myassignment.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myassignment.Adapter.AdapterBill;
import com.example.myassignment.Adapter.AdapterCategory;
import com.example.myassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bill extends Fragment {
    ListView lvListBill;
    FirebaseFirestore db;
    AdapterBill adapterBill;
    ArrayList<com.example.myassignment.Model.Bill> bills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        db = FirebaseFirestore.getInstance();
        bills = new ArrayList<>();
        lvListBill = view.findViewById(R.id.lvListBill);
        db.collection("bill")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String billName = (String) document.getData().get("billName");
                                String date = "" + document.getData().get("day") + "/" + document.getData().get("month") + "/" + document.getData().get("year");

                                bills.add(new com.example.myassignment.Model.Bill(billName, date));
                            }
                            adapterBill = new AdapterBill(bills);
                            lvListBill.setAdapter(adapterBill);
                        } else {
                        }
                    }
                });
        lvListBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder  =new AlertDialog.Builder(getContext());
                builder.setTitle("Xóa hóa đơn này");
                builder.setMessage("Bạn có chắc chắn xóa không?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("bill")
                                .whereEqualTo("billName", bills.get(position).getBillName())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            db.collection("bill").document(task.getResult().getDocuments().get(0).getId())
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                            bills.remove(position);
                                                            adapterBill.notifyDataSetChanged();

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                        }
                                                    });
                                        } else {
                                        }
                                    }
                                });
                    }
                });
                builder.show();
            }
        });
        return view;
    }
}
