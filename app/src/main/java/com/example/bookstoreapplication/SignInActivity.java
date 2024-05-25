package com.example.bookstoreapplication;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

/**
 * Activity for user sign in.
 */
public class SignInActivity extends BaseActivity {

    /** Adapter for Firebase Authentication */
    FirebaseAuthAdapter mAuthAdapter;

    /** EditText for user email input */
    EditText Email;

    /** EditText for user password input */
    EditText Password;

    /** Button for signing in */
    Button signInBtn;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuthAdapter = FirebaseAuthAdapter.getInstance();
        if (mAuthAdapter.isUserLoggedIn()) {
            startActivity(ActivityIntentFactory.createIntent(SignInActivity.this, CategoryActivity.class));
            finish();
        }

        Email = findViewById(R.id.UserName);
        Password = findViewById(R.id.Password);
        signInBtn = findViewById(R.id.signInBtn);
        TextView SignUP = findViewById(R.id.SignUP);

        signInBtn.setOnClickListener(view -> LoginUser());

        SignUP.setOnClickListener(view -> signUp());
    }

    /**
     * Navigates to the sign-up activity.
     */
    public void signUp() {
        Intent up = ActivityIntentFactory.createIntent(this, SignUpActivity.class);
        startActivity(up);
    }

    /**
     * Logs in the user with the provided email and password.
     * Validates the input fields and shows appropriate error messages if validation fails.
     */
    private void LoginUser() {
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        } else {
            mAuthAdapter.signInWithEmailAndPassword(email, password, (success, message) -> {
                if (success) {
                    Toast.makeText(SignInActivity.this, "SignIn Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(ActivityIntentFactory.createIntent(SignInActivity.this, CategoryActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "SignIn Failed: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void signIn(View view) {
    }
}

/**
 * Singleton pattern class for Firebase Authentication.
 */
class FirebaseAuthAdapter {
    private static FirebaseAuthAdapter instance;
    FirebaseAuth mAuth;

    /**
     * Private constructor to prevent instantiation.
     * Initializes the FirebaseAuth instance.
     */
    private FirebaseAuthAdapter() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Returns the single instance of FirebaseAuthAdapter.
     * @return The singleton instance of FirebaseAuthAdapter.
     */
    public static FirebaseAuthAdapter getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthAdapter();
        }
        return instance;
    }

    /**
     * Checks if a user is currently logged in.
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    /**
     * Signs in a user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @param callback The callback to be called on completion.
     */
    public void signInWithEmailAndPassword(String email, String password, FirebaseAuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onComplete(true, null);
                    } else {
                        callback.onComplete(false, Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    /**
     * Callback interface for Firebase Authentication results.
     */
    public interface FirebaseAuthCallback {
        /**
         * Called when the authentication process is complete.
         * @param success True if authentication was successful, false otherwise.
         * @param message The error message if authentication failed, null otherwise.
         */
        void onComplete(boolean success, String message);
    }
}

/**
 * Factory pattern class for creating intents.
 */
class ActivityIntentFactory {
    /**
     * Creates an intent for the specified context and class.
     * @param context The context from which the intent is created.
     * @param cls The class to which the intent navigates.
     * @return The created intent.
     */
    public static Intent createIntent(android.content.Context context, Class<?> cls) {
        return new Intent(context, cls);
    }
}
