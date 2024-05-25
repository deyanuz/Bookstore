package com.example.bookstoreapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Auth {
    private static final String TAG = "Auth";
    private static Auth instance;
    private final FirebaseAuth mAuth;

    private final FirebaseFirestore firestore;
    private final FirebaseUser firebaseUser;
    protected Auth() {
        mAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        firebaseUser=mAuth.getCurrentUser();
    }

    protected Auth(FirebaseAuth auth,FirebaseFirestore firestore,FirebaseUser firebaseUser) {
        this.mAuth = auth;
        this.firestore=firestore;
        this.firebaseUser=firebaseUser;
    }

    public static synchronized Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }
    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public String getUserUid() {
        String uid=firebaseUser.getUid();
        return  uid;
    }
}
