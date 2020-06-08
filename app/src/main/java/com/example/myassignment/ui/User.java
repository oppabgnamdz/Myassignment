package com.example.myassignment.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myassignment.Adapter.AdapterUser;
import com.example.myassignment.Login;
import com.example.myassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User extends Fragment {
    Button btnShow;
    ListView lvListUser;
    TextView tvName;
    AdapterUser adapterUser;
    FirebaseFirestore db;
    ArrayList<com.example.myassignment.Model.User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        db = FirebaseFirestore.getInstance();
        users = new ArrayList<com.example.myassignment.Model.User>();
        tvName = view.findViewById(R.id.tvName);

        btnShow = view.findViewById(R.id.btnShow);
        lvListUser = view.findViewById(R.id.lvListUser);
        handLing();
        return view;

    }

    public void handLing() {
        tvName.setText("Xin chào " + Login.userNameLogin + " !");
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String phoneNumber = null;
                                String address = null;

                                if (document.getData().get("phoneNumber") == null) {
                                    phoneNumber = "chưa nhập";
                                }
                                else {
                                    phoneNumber= (String) document.getData().get("phoneNumber");
                                }
                                if (document.getData().get("address") == null) {
                                    address = "chưa nhập";
                                }else {
                                    address= (String) document.getData().get("address");
                                }
                                users.add(new com.example.myassignment.Model.User(document.getData().get("userName").toString(), document.get("password").toString(), phoneNumber, address));
                            }
                            adapterUser = new AdapterUser(users);
                            lvListUser.setAdapter(adapterUser);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });

    }
}
