package com.example.bookstoreapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity for user sign up.
 */
public class SignUpActivity extends BaseActivity {

    /** EditText for user email input */
    EditText etmail;

    /** EditText for user password input */
    EditText tpassword;

    /** EditText for confirming password */
    EditText confirm;

    /** EditText for user name input */
    EditText name;

    /** Button for signing up */
    Button signUpButton;

    /** Button for users who already have an account */
    Button haveAccountButton;

    /** Firebase authentication instance */
    FirebaseAuth mAuth;

    //FirebaseDatabase database;
    //DatabaseReference reference;

    /** Factory for creating FirebaseAuth instances */
    public FirebaseAuthFactory mAuthFactory = FirebaseAuthFactory.getInstance();

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        //mAuth=FirebaseAuth.getInstance();

        mAuth = mAuthFactory.getFirebaseAuthInstance();

        // Sign up button
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> {
            createUser();
            //database=FirebaseDatabase.getInstance();
            // reference=database.getReference("users");
        });

        // Already have an account button
        haveAccountButton = (Button) findViewById(R.id.haveAccountButton);
        etmail = (EditText) findViewById(R.id.phone);
        tpassword = (EditText) findViewById(R.id.password);
        confirm = (EditText) findViewById(R.id.confirmPassword);
        name = (EditText) findViewById(R.id.username);

        haveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Creates a new user with the provided email, password, and username.
     * Validates the input fields and shows appropriate error messages if validation fails.
     */
    void createUser() {
        String email = etmail.getText().toString();
        String password = tpassword.getText().toString();
        String username = name.getText().toString();
        Log.d("fml: ", username);
        String confirm_pass = confirm.getText().toString();
        //reference.child(username);

        if (TextUtils.isEmpty(email)) {
            etmail.setError("Email cannot be empty");
            etmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            tpassword.setError("Password cannot be empty");
            tpassword.requestFocus();
        } else if (TextUtils.isEmpty(username)) {
            name.setError("Name cannot be empty");
            name.requestFocus();
        } else if (TextUtils.isEmpty(confirm_pass)) {
            confirm.setError("Passwords do not match");
            confirm.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username).build();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            }
                        });

                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
