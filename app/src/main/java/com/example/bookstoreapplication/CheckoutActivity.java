package com.example.bookstoreapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.HashMap;

public class CheckoutActivity extends BaseActivity {

    private RecyclerView rv;
    private CartItemAdapter adapter;
    protected int total = 20;
    private LinearLayout cardInfo;
    private Button submit;
    EditText ename,ephone,ecountry,estreet;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    CollectionReference colRef = db.collection("Cart").document("Cart").collection(userId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        rv = (RecyclerView) findViewById(R.id.myRecycler);
        ename=findViewById(R.id.editTextName);
        ephone=findViewById(R.id.editTextMobile);

        ecountry=findViewById(R.id.editTextCountryCity);
        estreet=findViewById(R.id.editTextStreetBuilding);


        ArrayList<HashMap<String, Object>> items = new ArrayList<>();
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    for(DocumentSnapshot doc : task.getResult().getDocuments()) {
                        HashMap<String, Object> map =  (HashMap<String, Object>) doc.getData();
                        items.add(map);
                        total = total + Integer.parseInt((String)map.get("Price"));
                    }
                    adapter = new CartItemAdapter(CheckoutActivity.this, items, false);

                    LinearLayoutManager layout = new LinearLayoutManager(CheckoutActivity.this);
                    rv.setLayoutManager(layout);
                    rv.setAdapter(adapter);

                    DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), layout.getOrientation());
                    divider.setDrawable(getDrawable(R.drawable.gradient_divider));
                    rv.addItemDecoration(divider);

                    ((TextView) findViewById(R.id.totalFee)).setText(total + " taka");

                }
            }
        });
        cardInfo = (LinearLayout) findViewById(R.id.cardInfo);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ename.getText().toString().isEmpty() || ecountry.getText().toString().isEmpty() || ephone.getText().toString().isEmpty() || estreet.getText().toString().isEmpty()){
                    Toast.makeText(CheckoutActivity.this, "Please Fill Delivery Info", Toast.LENGTH_SHORT).show();

                }else{
                    CollectionReference colRef2 = db.collection("History").document("History").collection(userId);
                    ArrayList<HashMap<String,Object>> hitems=new ArrayList<>();
                    colRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    HashMap<String, Object> map = (HashMap<String, Object>) doc.getData();
                                    hitems.add(map);
                                }
                                Collections.reverse(hitems);
                            }
                        }
                    });
                    Collections.reverse(items);
                    for(HashMap<String,Object> item:items){
                        hitems.add(item);
                    }
                    Collections.reverse(hitems);
                    colRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    doc.getReference().delete();
                                }

                                for (HashMap<String, Object> item : hitems) {
                                    colRef2.add(item);
                                }
                            }
                        }
                    });
                    clearCart();
                    Intent i = new Intent(CheckoutActivity.this, CheckoutDoneActivity.class);
                    startActivity(i);
                }

            }
        });
    }
    private void clearCart() {
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        doc.getReference().delete();
                    }
                } else {
                    Toast.makeText(CheckoutActivity.this, "Failed to clear the cart. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editPayOptions(View v) {
        int viewId = v.getId();

        if (viewId == R.id.cash) {
            cardInfo.setVisibility(View.GONE);
        } else if (viewId == R.id.payNow) {
            cardInfo.setVisibility(View.VISIBLE);
            cardInfo.setFocusable(true);
            cardInfo.setFocusableInTouchMode(true);
            cardInfo.requestFocus();
        }
    }

}