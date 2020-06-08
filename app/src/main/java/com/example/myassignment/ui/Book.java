package com.example.myassignment.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myassignment.Adapter.AdapterBook;
import com.example.myassignment.Database;
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
 * Use the {@link Book#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Book extends Fragment {
    ListView lvListBook;
    AdapterBook adapterBook;
    ArrayList<com.example.myassignment.Model.Book> books;
    FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        lvListBook = view.findViewById(R.id.lvListBook);
        books = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("book")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("data",task.getResult().size()+"");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String bookName = (String) document.getData().get("bookName");
                                String categoryBook = (String) document.getData().get("bookName");
                                String date = (String) document.getData().get("date");
                                String author = (String) document.getData().get("author");
                                String title = (String) document.getData().get("title");
                                String money = (String) document.getData().get("money");
                                String number = (String) document.getData().get("number");
                                books.add(new com.example.myassignment.Model.Book(bookName, categoryBook, title, author, date, money, number));
                                adapterBook = new AdapterBook(books);

                                lvListBook.setAdapter(adapterBook);
                                adapterBook.notifyDataSetChanged();
                            }
                        } else {
                        }
                    }
                });
        lvListBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View viewBook = LayoutInflater.from(getContext()).inflate(R.layout.layout_update_book,null);
                final EditText edtMoney = viewBook.findViewById(R.id.edtMoney);
                final EditText edtNumber = viewBook.findViewById(R.id.edtNumber);
                Button btnConfirm = viewBook.findViewById(R.id.btnConfirm);
                Button btnCancel = viewBook.findViewById(R.id.btnCancel);
                Button btnDelete = viewBook.findViewById(R.id.btnDelete);
                builder.setView(viewBook);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("book")
                                .whereEqualTo("bookName", books.get(position).getBookName())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            db.collection("book").document(task.getResult().getDocuments().get(0).getId())
                                                    .update("money", edtMoney.getText().toString(), "number", edtNumber.getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                                            String bookname = (String) task.getResult().getDocuments().get(0).getData().get("bookName");
                                                            String categoryName = (String) task.getResult().getDocuments().get(0).getData().get("categoryName");
                                                            String title = (String) task.getResult().getDocuments().get(0).getData().get("title");
                                                            String date = (String) task.getResult().getDocuments().get(0).getData().get("date");
                                                            String author = (String) task.getResult().getDocuments().get(0).getData().get("author");
                                                            com.example.myassignment.Model.Book book = new com.example.myassignment.Model.Book(bookname,categoryName,title,author,date,edtMoney.getText().toString(),edtNumber.getText().toString());
                                                            books.remove(position);
                                                            books.add(position,book);
                                                            adapterBook.notifyDataSetChanged();
                                                            alertDialog.dismiss();
                                                        }
                                                    });

                                        } else {
                                        }
                                    }
                                });
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("book")
                                .whereEqualTo("bookName", books.get(position).getBookName())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            db.collection("book").document(task.getResult().getDocuments().get(0).getId())
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                            books.remove(position);
                                                            adapterBook.notifyDataSetChanged();
                                                            alertDialog.dismiss();
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
            }
        });
        return view;

    }
}
