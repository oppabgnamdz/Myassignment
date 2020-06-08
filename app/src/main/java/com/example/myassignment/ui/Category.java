package com.example.myassignment.ui;

import android.app.AlertDialog;
import android.os.Bundle;
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

import com.example.myassignment.Adapter.AdapterCategory;
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
 * Use the {@link Category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category extends Fragment {
    ListView lvListCategory;
    AdapterCategory adapterCategory;
    FirebaseFirestore db;
    ArrayList<com.example.myassignment.Model.Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        lvListCategory = view.findViewById(R.id.lvListCategory);
        categories = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        try {
            db.collection("category")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = (String) document.getData().get("name");
                                    String description = (String) document.getData().get("description");
                                    String position = (String) document.getData().get("position");

                                    categories.add(new com.example.myassignment.Model.Category(name, description, position));
                                }
                                adapterCategory = new AdapterCategory(categories);
                                lvListCategory.setAdapter(adapterCategory);
                            } else {
                            }
                        }
                    });

        } catch (Exception e) {

        }
        lvListCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View viewCategory = LayoutInflater.from(getContext()).inflate(R.layout.layout_update_category, null);
                final EditText edtDescription = viewCategory.findViewById(R.id.edtDescription);
                final EditText edtPosition = viewCategory.findViewById(R.id.edtPosition);
                Button btnCancel = viewCategory.findViewById(R.id.btnCancel);
                Button btnDelete = viewCategory.findViewById(R.id.btnDelete);
                Button btnUpdate = viewCategory.findViewById(R.id.btnUpdate);
                builder.setView(viewCategory);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("category")
                                .whereEqualTo("name", categories.get(position).getName())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            db.collection("category").document(task.getResult().getDocuments().get(0).getId())
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                            categories.remove(position);
                                                            adapterCategory.notifyDataSetChanged();
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
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("category")
                                .whereEqualTo("name", categories.get(position).getName())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            db.collection("category").document(task.getResult().getDocuments().get(0).getId())
                                                    .update("description", edtDescription.getText().toString(), "position", edtPosition.getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                                            categories.remove(position);
                                                            categories.add(position, new com.example.myassignment.Model.Category((String) task.getResult().getDocuments().get(0).getData().get("name"), edtDescription.getText().toString(), edtPosition.getText().toString()));
                                                            adapterCategory.notifyDataSetChanged();
                                                            alertDialog.dismiss();
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
