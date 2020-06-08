package com.example.myassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myassignment.Database;
import com.example.myassignment.Login;
import com.example.myassignment.MainActivity;
import com.example.myassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePass extends Fragment {
    EditText edtPassCurrent, edtPassNew, edtPassConfirm;
    Button btnConfirm, btnCancel;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        edtPassCurrent = view.findViewById(R.id.edtPassCurrent);
        edtPassNew = view.findViewById(R.id.edtPassNew);
        edtPassConfirm = view.findViewById(R.id.edtPassConfirm);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnCancel = view.findViewById(R.id.btnCancel);
        db =  FirebaseFirestore.getInstance();
        handling();
        return view;
    }

    public void handling() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").whereEqualTo("userName", Login.userNameLogin).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(edtPassNew.getText().toString().equals(edtPassConfirm.getText().toString())){
                                if(edtPassCurrent.getText().toString().equals(task.getResult().getDocuments().get(0).getData().get("password"))){
                                    db.collection("users").document(task.getResult().getDocuments().get(0).getId())
                                            .update("password",edtPassNew.getText().toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }else {
                                    Toast.makeText(getContext(), "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(getContext(), "Mật khẩu xác nhận không trùng", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPassCurrent.setText("");
                edtPassConfirm.setText("");
                edtPassNew.setText("");
            }
        });
    }
}
