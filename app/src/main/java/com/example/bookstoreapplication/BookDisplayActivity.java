package com.example.bookstoreapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity to display books based on selected criteria.
 */
public class BookDisplayActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private CollectionReference colRef;
    private int[] value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);

        value = getIntent().getIntArrayExtra("Values");

        recyclerView = findViewById(R.id.recyclerview);

        colRef = ReferenceFactory.getCollectionReference(value);

        if (colRef != null) {
            Log.e("Path", colRef.getPath());
            Log.d("Path: ", colRef.getPath());

            colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d("Path: ", "Success");
                        ArrayList<HashMap<String, Object>> items = new ArrayList<>();

                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            HashMap<String, Object> map = (HashMap<String, Object>) doc.getData();
                            Log.e("hey ", map.toString());
                            items.add(map);
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(BookDisplayActivity.this));
                        recyclerView.setAdapter(new MyAdapter(BookDisplayActivity.this, items));
                    }
                }
            });
        }
    }
}

/**
 * Factory class to create Firebase collection references based on provided criteria.
 */
class ReferenceFactory {

    /**
     * Creates a CollectionReference based on the provided values.
     *
     * @param value An array of integers representing the criteria for selecting the collection.
     * @return The appropriate CollectionReference based on the criteria.
     */
    public static CollectionReference getCollectionReference(int[] value) {
        DocumentReference docRef;
        CollectionReference colRef = null;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        switch (value[0]) {
            case 0:
                docRef = db.collection("English").document("engid");
                break;
            case 1:
                docRef = db.collection("Bangla").document("bangid");
                break;
            default:
                return null;
        }

        switch (value[1]) {
            case 0:
                docRef = docRef.collection("Books").document(value[0] == 0 ? "bookeng" : "bookbang");
                break;
            case 1:
                docRef = docRef.collection("Novels").document(value[0] == 0 ? "novengid" : "novbangid");
                break;
            default:
                return null;
        }

        switch (value[2]) {
            case 0:
                colRef = docRef.collection("Adventure");
                break;
            case 1:
                colRef = docRef.collection("Crime");
                break;
            case 2:
                colRef = docRef.collection("Drama");
                break;
            case 3:
                colRef = docRef.collection("Fantasy");
                break;
            case 4:
                colRef = docRef.collection("Fiction");
                break;
            case 5:
                colRef = docRef.collection("Horror");
                break;
            case 6:
                colRef = docRef.collection("Realism");
                break;
            case 7:
                colRef = docRef.collection("Romance");
                break;
            default:
                return null;
        }
        return colRef;
    }
}
