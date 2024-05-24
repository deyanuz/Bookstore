package com.example.bookstoreapplication;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthFactory {
    private static FirebaseAuthFactory instance;
    private static FirebaseAuth mAuth;

    private FirebaseAuthFactory() {
        // Prevent instantiation from outside
    }


    public static FirebaseAuthFactory getInstance() {
        if (instance == null) {
            synchronized (FirebaseAuthFactory.class) {
                if (instance == null) {
                    instance = new FirebaseAuthFactory();
                    mAuth = FirebaseAuth.getInstance();
                }
            }
        }
        return instance;
    }

    public FirebaseAuth getFirebaseAuthInstance() {
        return mAuth;
    }
}
