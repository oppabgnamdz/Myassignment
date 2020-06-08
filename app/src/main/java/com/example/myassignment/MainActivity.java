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
import android.widget.DatePicker;
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
                                final Map<String, Object> category = new HashMap<>();
                                category.put("name", name);
                                category.put("description", description);
                                category.put("position", position);
                                db.collection("category")
                                        .whereEqualTo("name", name)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().size() > 0) {
                                                        Toast.makeText(MainActivity.this, "Thể loại này đã có rồi", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    } else {
                                                        db.collection("category")
                                                                .add(category)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Toast.makeText(MainActivity.this, "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
                                                                        dialog.dismiss();
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
                        AlertDialog.Builder builderStatistic = new AlertDialog.Builder(MainActivity.this);
                        View viewStatistic = getLayoutInflater().inflate(R.layout.layout_insert_statistic, null);
                        final Spinner spnBillName = viewStatistic.findViewById(R.id.spnBillName);
                        final Spinner spnBookName = viewStatistic.findViewById(R.id.spnBookName);
                        final EditText edtNumberStatistic = viewStatistic.findViewById(R.id.edtNumber);
                        Button btnCancelStatistic = viewStatistic.findViewById(R.id.btnCancel);
                        Button btnConfirmStatistic = viewStatistic.findViewById(R.id.btnConfirm);
                        final ArrayList<String> billNames = new ArrayList<>();
                        final ArrayList<String> bookNames = new ArrayList<>();
                        db.collection("bill")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                billNames.add((String) document.getData().get("billName"));
                                            }
                                            ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, billNames);
                                            spnBillName.setAdapter(arrayAdapter);
                                        } else {
                                        }
                                    }
                                });
                        db.collection("book")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                bookNames.add((String) document.getData().get("bookName"));
                                            }
                                            ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, bookNames);
                                            spnBookName.setAdapter(arrayAdapter);
                                        } else {
                                        }
                                    }
                                });
                        builderStatistic.setView(viewStatistic);

                        final AlertDialog alertDialogStatistic = builderStatistic.create();
                        alertDialogStatistic.show();
                        btnCancelStatistic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialogStatistic.dismiss();
                            }
                        });
                        btnConfirmStatistic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String billName = spnBillName.getSelectedItem().toString();
                                final String bookName = spnBookName.getSelectedItem().toString();
                                final String numberStatistic = edtNumberStatistic.getText().toString();
                                final Map<String, Object> statistic = new HashMap<>();
                                statistic.put("billName", billName);
                                statistic.put("bookName", bookName);
                                statistic.put("number", numberStatistic);
                                db.collection("statistic")
                                        .whereEqualTo("billName", billName)
                                        .whereEqualTo("bookName", bookName)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().size() > 0) {
                                                        Toast.makeText(MainActivity.this, "Hóa đơn chi tiết này có rồi", Toast.LENGTH_SHORT).show();
                                                        alertDialogStatistic.dismiss();
                                                    } else {
                                                        db.collection("book").whereEqualTo("bookName", bookName)
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                                                                        if (Integer.valueOf((String) task.getResult().getDocuments().get(0).get("number")) - Integer.valueOf(numberStatistic) >= 0) {
                                                                            db.collection("statistic")
                                                                                    .add(statistic)
                                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                        @Override
                                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                                            db.collection("book").document(task.getResult().getDocuments().get(0).getId())
                                                                                                    .update("number", String.valueOf(Integer.valueOf((String) task.getResult().getDocuments().get(0).get("number")) - Integer.valueOf(numberStatistic)))
                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                            Log.e("thành công", "thành công");
                                                                                                        }
                                                                                                    });
                                                                                            Toast.makeText(MainActivity.this, "Thêm hóa đơn chi tiết thành công", Toast.LENGTH_SHORT).show();
                                                                                            alertDialogStatistic.dismiss();
                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Log.e("TAG", "Error adding document", e);
                                                                                        }
                                                                                    });


                                                                        }else {
                                                                            Toast.makeText(MainActivity.this, "Số lượng nhập lớn hơn số sách đang có", Toast.LENGTH_SHORT).show();
                                                                        }
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


                        break;
                    case "book":
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
                        final ArrayList<String> strings = new ArrayList<>();
                        db.collection("category")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                strings.add((String) document.getData().get("name"));
                                            }
                                            ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, strings);
                                            spinner.setAdapter(arrayAdapter);
                                        } else {
                                        }
                                    }
                                });

                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        btnConfirm1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String bookName = edtBookName.getText().toString();
                                String categoryBook = spinner.getSelectedItem().toString();
                                String title = edtTitle.getText().toString();
                                String author = edtAuthor.getText().toString();
                                String date = edtDate.getText().toString();
                                String money = edtMoney.getText().toString();
                                String number = edtNumber.getText().toString();
                                final Map<String, Object> book = new HashMap<>();
                                book.put("bookName", bookName);
                                book.put("categoryBook", categoryBook);
                                book.put("title", title);
                                book.put("date", date);
                                book.put("money", money);
                                book.put("number", number);
                                book.put("author", author);
                                db.collection("book")
                                        .whereEqualTo("bookName", bookName)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().size() > 0) {
                                                        Toast.makeText(MainActivity.this, "Sách này đã có rồi", Toast.LENGTH_SHORT).show();
                                                        alertDialog.dismiss();
                                                    } else {
                                                        db.collection("book")
                                                                .add(book)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Toast.makeText(MainActivity.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                                                                        alertDialog.dismiss();
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
                        AlertDialog.Builder builderBill = new AlertDialog.Builder(MainActivity.this);
                        View viewBill = getLayoutInflater().inflate(R.layout.layout_insert_bill, null);
                        builderBill.setView(viewBill);
                        final EditText edtBillName = viewBill.findViewById(R.id.edtBillName);
                        final DatePicker datePicker = viewBill.findViewById(R.id.datePicker);
                        Button btnConfirmBill = viewBill.findViewById(R.id.btnConfirm);
                        Button btnCancelBill = viewBill.findViewById(R.id.btnCancel);
                        final AlertDialog alerDiaLogBill = builderBill.create();
                        alerDiaLogBill.show();
                        btnCancelBill.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alerDiaLogBill.dismiss();
                            }
                        });
                        btnConfirmBill.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String billName = edtBillName.getText().toString();
                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth() + 1;
                                int year = datePicker.getYear();
                                final Map<String, Object> bill = new HashMap<>();
                                bill.put("billName", billName);
                                bill.put("day", day);
                                bill.put("month", month);
                                bill.put("year", year);

                                db.collection("bill")
                                        .whereEqualTo("billName", billName)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().size() > 0) {
                                                        Toast.makeText(MainActivity.this, "Mã hóa đơn đã có rồi", Toast.LENGTH_SHORT).show();
                                                        alerDiaLogBill.dismiss();
                                                    } else {
                                                        db.collection("bill")
                                                                .add(bill)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Toast.makeText(MainActivity.this, "Thêm hóa dơn thành công", Toast.LENGTH_SHORT).show();
                                                                        alerDiaLogBill.dismiss();
                                                                        navController.navigate(R.id.nav_bill);
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
