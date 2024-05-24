package com.example.bookstoreapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Activity to display book details and provide an option to add the book to the cart.
 */
public class BookDetailActivity extends BaseActivity implements Observer {

    private TextView title;
    private TextView author;
    private TextView price;
    private TextView overview;
    private ImageView cover;
    private HashMap<String, Object> data;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        cover = findViewById(R.id.imageView2);
        title = findViewById(R.id.textView);
        author = findViewById(R.id.textView3);
        price = findViewById(R.id.textView6);
        overview = findViewById(R.id.textView2);

        data = (HashMap<String, Object>) getIntent().getSerializableExtra("data");

        title.setText((String) data.get("Name"));
        author.setText("Author:\n" + (String) data.get("Author"));
        int p = Integer.parseInt((String) data.get("Price"));
        price.setText(p + " taka");
        overview.setText((String) data.get("Overview"));

        try {
            Uri uri = Uri.parse((String) data.get("Cover"));
            ImageLoader.getInstance().loadImage(uri, cover);
        } catch (NullPointerException exception) {
            // Handle exception
        }

        Button toCart = findViewById(R.id.AddToCart);
        toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToCartCommand command = new AddToCartCommand(
                        FirebaseUtil.getFirestoreInstance(),
                        FirebaseUtil.getAuthInstance().getCurrentUser().getUid(),
                        data,
                        BookDetailActivity.this
                );
                CartObservable.getInstance().addObserver(BookDetailActivity.this);
                command.execute();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CartObservable) {
            boolean success = (boolean) arg;
            if (success) {
                Intent toCart = new Intent(BookDetailActivity.this, CartActivity.class);
                startActivity(toCart);
            } else {
                Toast.makeText(BookDetailActivity.this, "Error Occurred. Couldn't add to cart.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Command to add a book to the cart.
     */
    public static class AddToCartCommand {
        private final FirebaseFirestore db;
        private final String userId;
        private final HashMap<String, Object> data;
        private final Observer observer;

        public AddToCartCommand(FirebaseFirestore db, String userId, HashMap<String, Object> data, Observer observer) {
            this.db = db;
            this.userId = userId;
            this.data = data;
            this.observer = observer;
        }

        public void execute() {
            DocumentReference ref = db.collection("Cart").document("Cart").collection(userId).document((String) data.get("Name"));
            ref.set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            CartObservable.getInstance().setChangedAndNotify(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            CartObservable.getInstance().setChangedAndNotify(false);
                        }
                    });
        }
    }

    /**
     * Utility class for Firebase instances.
     */
    public static class FirebaseUtil {
        private static FirebaseFirestore dbInstance;
        private static FirebaseAuth authInstance;

        public static FirebaseFirestore getFirestoreInstance() {
            if (dbInstance == null) {
                dbInstance = FirebaseFirestore.getInstance();
            }
            return dbInstance;
        }

        public static FirebaseAuth getAuthInstance() {
            if (authInstance == null) {
                authInstance = FirebaseAuth.getInstance();
            }
            return authInstance;
        }
    }

    /**
     * Observable class for cart updates.
     */
    public static class CartObservable extends Observable {
        private static CartObservable instance;

        public static CartObservable getInstance() {
            if (instance == null) {
                instance = new CartObservable();
            }
            return instance;
        }

        public void setChangedAndNotify(boolean success) {
            setChanged();
            notifyObservers(success);
        }
    }

    /**
     * Adapter class for loading images using Glide.
     */
    public static class ImageLoader {
        private static ImageLoader instance;

        public static ImageLoader getInstance() {
            if (instance == null) {
                instance = new ImageLoader();
            }
            return instance;
        }

        public void loadImage(Uri uri, ImageView imageView) {
            Glide.with(imageView.getContext()).load(uri).into(imageView);
        }
    }
}
