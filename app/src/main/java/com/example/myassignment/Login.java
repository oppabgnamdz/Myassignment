package com.example.myassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myassignment.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    public static String userNameLogin;
    CardView cardViewSignIn, cardViewSignUp;
    EditText edtUser, edtPass;
    CheckBox checkInfor;
    SharedPreferences sharedPreferences;
    Database database;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("myLogin", MODE_PRIVATE);
        init();


        cardViewSignIn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                final String userName = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                db = FirebaseFirestore.getInstance();
                final Map<String, Object> user = new HashMap<>();
                user.put("userName", userName);
                user.put("password", pass);
                db.collection("users")
                        .whereEqualTo("userName", userName)
                        .whereEqualTo("password",pass)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(task.getResult().size()>0){
                                        userNameLogin=userName;
                                        Intent intent = new Intent(Login.this,MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(Login.this, "Nhập tài khoản không đúng", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });


                if (checkInfor.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", edtUser.getText().toString());
                    editor.putString("pass", edtPass.getText().toString());
                    editor.putBoolean("check", true);
                    editor.commit();
                }

            }
        });
        cardViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                db = FirebaseFirestore.getInstance();
                final Map<String, Object> user = new HashMap<>();
                user.put("userName", userName);
                user.put("password", pass);
                db.collection("users")
                        .whereEqualTo("userName", userName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() > 0) {
                                        Toast.makeText(Login.this, "Tài khoản lập rồi", Toast.LENGTH_SHORT).show();
                                    } else {
                                        db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(Login.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("TAG", "Error adding document", e);
                                                    }
                                                });
                                    }

                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });


            }
        });
        checkInfor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkInfor.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", edtUser.getText().toString());
                    editor.putString("pass", edtPass.getText().toString());
                    editor.putBoolean("check", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.putBoolean("check", false);
                    editor.commit();
                }
            }
        });
    }

    private void init() {
        String user = sharedPreferences.getString("user", "");
        String pass = sharedPreferences.getString("pass", "");
        boolean check = sharedPreferences.getBoolean("check", false);
        cardViewSignIn = findViewById(R.id.cardView);
        cardViewSignUp = findViewById(R.id.cardViewSignUp);
        edtUser = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPass);
        checkInfor = findViewById(R.id.checkInfor);
        edtUser.setText(user);
        edtPass.setText(pass);
        checkInfor.setChecked(check);
        database = new Database(getApplicationContext());
    }
}
