package com.example.myassignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myassignment.Model.Book;
import com.example.myassignment.Model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    String screen;
    Database database;
    NavController navController;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        db = FirebaseFirestore.getInstance();
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (screen) {
                    case "home":
                        Toast.makeText(MainActivity.this, "Bạn đang ở Home", Toast.LENGTH_SHORT).show();
                        break;
                    case "best_sale":
                        Toast.makeText(MainActivity.this, "Bạn đang ở bán chạy nhất", Toast.LENGTH_SHORT).show();
                        break;
                    case "category":
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.layout_insert_category);
                        final EditText edtName = dialog.findViewById(R.id.edtName);
                        final EditText edtDescription = dialog.findViewById(R.id.edtDescription);
                        final EditText edtPosition = dialog.findViewById(R.id.edtPosition);
                        final Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = edtName.getText().toString();
                                String description = edtDescription.getText().toString();
                                String position = edtPosition.getText().toString();
                                Map<String, Object> category = new HashMap<>();
                                category.put("name", name);
                                category.put("description", description);
                                category.put("position", position);
                                db.collection("category")
                                        .add(category)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });

                                navController.navigate(R.id.nav_category);
                            }
                        });
                        Button btnCancel = dialog.findViewById(R.id.btnCancel);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case "statistic":
                        Toast.makeText(MainActivity.this, "Bạn đang ở thống kê", Toast.LENGTH_SHORT).show();
                        break;
                    case "book":
                        database = new Database(getApplicationContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        View view1 = getLayoutInflater().inflate(R.layout.layout_insert_book, null);
                        final EditText edtBookName = view1.findViewById(R.id.edtBookName);
                        final EditText edtTitle = view1.findViewById(R.id.edtTitle);
                        final EditText edtAuthor = view1.findViewById(R.id.edtAuthor);
                        final EditText edtDate = view1.findViewById(R.id.edtDate);
                        final EditText edtMoney = view1.findViewById(R.id.edtMoney);
                        final EditText edtNumber = view1.findViewById(R.id.edtMoney);
                        Button btnConfirm1 = view1.findViewById(R.id.btnConfirm);
                        Button btnCancel1 = view1.findViewById(R.id.btnCancel);
                        final Spinner spinner = view1.findViewById(R.id.spinner);
                        builder.setView(view1);
                        ArrayList<String> strings = new ArrayList<>();
                        for (int i = 0; i < database.getAllCategory().size(); i++) {
                            strings.add(database.getAllCategory().get(i).getName());
                        }
                        Log.e("data", strings + "");
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, strings);
                        spinner.setAdapter(arrayAdapter);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        btnConfirm1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database = new Database(getApplicationContext());
                                String bookName = edtBookName.getText().toString();
                                String categoryBook = spinner.getSelectedItem().toString();
                                String title = edtTitle.getText().toString();
                                String author = edtAuthor.getText().toString();
                                String date = edtDate.getText().toString();
                                long money = Long.parseLong(edtMoney.getText().toString());
                                int number = Integer.parseInt(edtNumber.getText().toString());
                                long result = database.inserBook(new Book(bookName, categoryBook, title, author, date, money, number));
                                if (result > 0) {
                                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                }
                                navController.navigate(R.id.nav_book);
                                alertDialog.dismiss();
                            }
                        });
                        btnCancel1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                        break;
                    case "bill":
                        Toast.makeText(MainActivity.this, "Bạn đang ở hóa đơn", Toast.LENGTH_SHORT).show();
                        break;
                    case "user":
                        AlertDialog.Builder builderUser = new AlertDialog.Builder(MainActivity.this);
                        View viewUser = getLayoutInflater().inflate(R.layout.layout_insert_user, null);
                        final EditText edtPhoneNumber = viewUser.findViewById(R.id.edtPhoneNumber);
                        final EditText edtAddress = viewUser.findViewById(R.id.edtAddress);
                        Button btnCancelUser = viewUser.findViewById(R.id.btnCancel);
                        Button btnConfirmUser = viewUser.findViewById(R.id.btnConfirm);
                        builderUser.setView(viewUser);

                        final AlertDialog dialogUser = builderUser.create();
                        dialogUser.show();
                        btnCancelUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogUser.dismiss();
                            }
                        });
                        btnConfirmUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                db.collection("users").whereEqualTo("userName", Login.userNameLogin).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            db.collection("users").document(task.getResult().getDocuments().get(0).getId())
                                                    .update("phoneNumber", edtPhoneNumber.getText().toString(), "address", edtAddress.getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(MainActivity.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                                                            navController.navigate(R.id.nav_user);
                                                            dialogUser.dismiss();
                                                        }
                                                    });
                                            dialogUser.dismiss();
                                        } else {
                                            Log.e("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });


                            }
                        });
                        break;
                    case "change_pass":
                        Toast.makeText(MainActivity.this, "Bạn đang ở đổi mật khẩu", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considere d as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category, R.id.nav_book, R.id.nav_bill, R.id.nav_best_sale, R.id.nav_statistic, R.id.nav_user, R.id.change_pass, R.id.log_out)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.nav_home:
                        screen = "home";
                        break;
                    case R.id.nav_category:
                        screen = "category";
                        break;
                    case R.id.nav_book:
                        screen = "book";
                        break;
                    case R.id.nav_bill:
                        screen = "bill";
                        break;
                    case R.id.nav_best_sale:
                        screen = "best_sale";
                        break;
                    case R.id.nav_statistic:
                        screen = "statistic";
                        break;
                    case R.id.nav_user:
                        screen = "user";
                        break;
                    case R.id.change_pass:
                        screen = "change_pass";
                        break;
                    case R.id.log_out:
                        System.exit(0);
                        break;
                }
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
