package com.example.bookstoreapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Manages the shopping cart activities within the bookstore app, allowing users to view
 * and manage the items in their shopping cart.
 */
public class CartActivity extends BaseActivity {

    public RecyclerView rv;
    public CartItemAdapter adapter;
    protected CollectionReference colRef;
    protected FirebaseFirestore firestore;
    private final Auth authUtility = Auth.getInstance();

    /**
     * Initializes the activity, sets up the RecyclerView for displaying cart items, and
     * defines the checkout process.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rv = findViewById(R.id.myRecycler);
        Button checkout = findViewById(R.id.checkout);

        // Use Auth instance to get FirebaseAuth and FirebaseFirestore instances
        firestore = authUtility.getFirestore();

        String userId = Objects.requireNonNull(authUtility.getUserUid());
        colRef = firestore.collection("Cart").document("Cart").collection(userId);

        // Fetch cart items from Firestore and update UI
        getRecyclerViewData();

        // Set up checkout button to lead to CheckoutActivity if cart is not empty
        checkout.setOnClickListener(view ->checkout());
    }

    /**
     * Sets up the RecyclerView with the cart items.
     *
     * @param items A list of cart items to be displayed in the RecyclerView.
     */
    private void setupRecyclerView(ArrayList<HashMap<String, Object>> items) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new CartItemAdapter(CartActivity.this, items, true);
        rv.setAdapter(adapter);
    }

    void getRecyclerViewData() {
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<HashMap<String, Object>> items = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        HashMap<String, Object> map = (HashMap<String, Object>) doc.getData();
                        items.add(map);
                    }
                    setupRecyclerView(items);
                }
            }
        });
    }

    void checkout() {
        colRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().getDocuments().isEmpty()) {
                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(CartActivity.this, "Cart is empty. Put some books in your cart first!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}