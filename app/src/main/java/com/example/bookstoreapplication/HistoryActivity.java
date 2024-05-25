package com.example.bookstoreapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends BaseActivity {
    private CollectionReference colRef;
    private RecyclerView rv;
    private CartItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rv = (RecyclerView) findViewById(R.id.myRecyclerHistory);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        colRef = db.collection("History").document("History").collection(userId);
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<HashMap<String, Object>> items = new ArrayList<>();

                    //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    for(DocumentSnapshot doc : task.getResult().getDocuments()){
                        HashMap<String, Object> map =  (HashMap<String, Object>) doc.getData();
                        items.add(map);
                    }
                    adapter = new CartItemAdapter(HistoryActivity.this, items, false);

                    LinearLayoutManager layout = new LinearLayoutManager(HistoryActivity.this);
                    rv.setLayoutManager(layout);
                    rv.setAdapter(adapter);

                    DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), layout.getOrientation());
                    divider.setDrawable(getDrawable(R.drawable.gradient_divider));
                    rv.addItemDecoration(divider);
                }
            }
        });
    }
}