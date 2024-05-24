package com.example.bookstoreapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for selecting book categories and languages.
 */
public class CategoryActivity extends BaseActivity {

    ConstraintLayout bookNovelLayout;
    ConstraintLayout categoryLayout;
    public int[] values = new int[3];

    private List<CategorySelectionListener> categoryListeners = new ArrayList<>();
    private List<BookLanguageSelectionListener> bookLanguageListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        TextView welcome = findViewById(R.id.Welcome);
        String msg = "Welcome!";
        welcome.setText(msg);

        Button horrorButton = findViewById(R.id.Horror);
        Button fictionButton = findViewById(R.id.Fiction);
        Button fantasyButton = findViewById(R.id.Fantasy);
        Button romanceButton = findViewById(R.id.Romance);
        Button crimeButton = findViewById(R.id.Crime);
        Button adventureButton = findViewById(R.id.Adventure);
        Button realismButton = findViewById(R.id.Realism);
        Button dramaButton = findViewById(R.id.Drama);

        bookNovelLayout = findViewById(R.id.BooksNovels);
        categoryLayout = findViewById(R.id.Category);
    }

    /**
     * Navigate to BookDisplayActivity with selected category and language.
     *
     * @param v The view that was clicked.
     */
    @SuppressLint("NonConstantResourceId")
    public void toBookDisplay(View v) {
        int[] idArray = {
                R.id.Adventure,
                R.id.Crime,
                R.id.Drama,
                R.id.Fantasy,
                R.id.Fiction,
                R.id.Horror,
                R.id.Realism,
                R.id.Romance
        };

        int selectedValue = -1;
        for (int i = 0; i < idArray.length; i++) {
            if (v.getId() == idArray[i]) {
                selectedValue = i;
                break;
            }
        }

        if (selectedValue != -1) {
            values[2] = selectedValue;
            Intent toBD = new Intent(CategoryActivity.this, BookDisplayActivity.class);
            toBD.putExtra("Values", values);
            startActivity(toBD);
        } else {
            // Handle case when no valid ID is selected
        }
    }

    /**
     * Handle book language selection.
     *
     * @param v The view that was clicked.
     */
    public void BookNovelView(View v) {
        int[] idArray = {
                R.id.English,
                R.id.Bangla
        };

        int selectedValue = -1;
        for (int i = 0; i < idArray.length; i++) {
            if (v.getId() == idArray[i]) {
                selectedValue = i;
                break;
            }
        }

        if (selectedValue != -1) {
            values[0] = selectedValue;
            bookNovelLayout.setVisibility(View.VISIBLE);
            if (v.getId() == R.id.English) {
                ((ToggleButton)findViewById(R.id.Bangla)).setChecked(false);
            } else {
                ((ToggleButton)findViewById(R.id.English)).setChecked(false);
            }
            notifyBookLanguageSelectionChanged();
        } else {
            bookNovelLayout.setVisibility(View.GONE);
        }
    }


    /**
     * Handle category selection.
     *
     * @param v The view that was clicked.
     */
    public void CategoryView(View v) {
        int[] idArray = {
                R.id.Books,
                R.id.Novels
        };

        int selectedValue = -1;
        for (int i = 0; i < idArray.length; i++) {
            if (v.getId() == idArray[i]) {
                selectedValue = i;
                break;
            }
        }

        if (selectedValue != -1) {
            values[1] = selectedValue;
            categoryLayout.setVisibility(View.VISIBLE);
            if (v.getId() == R.id.Books) {
                ((ToggleButton)findViewById(R.id.Novels)).setChecked(false);
            } else {
                ((ToggleButton)findViewById(R.id.Books)).setChecked(false);
            }
            notifyCategorySelectionChanged();
        } else {
            categoryLayout.setVisibility(View.GONE);
        }
    }


    // Observer pattern implementation

    /**
     * Add a listener for category selection changes.
     *
     * @param listener The listener to add.
     */
    public void addCategorySelectionListener(CategorySelectionListener listener) {
        categoryListeners.add(listener);
    }

    /**
     * Remove a listener for category selection changes.
     *
     * @param listener The listener to remove.
     */
    public void removeCategorySelectionListener(CategorySelectionListener listener) {
        categoryListeners.remove(listener);
    }

    /**
     * Add a listener for book language selection changes.
     *
     * @param listener The listener to add.
     */
    public void addBookLanguageSelectionListener(BookLanguageSelectionListener listener) {
        bookLanguageListeners.add(listener);
    }

    /**
     * Remove a listener for book language selection changes.
     *
     * @param listener The listener to remove.
     */
    public void removeBookLanguageSelectionListener(BookLanguageSelectionListener listener) {
        bookLanguageListeners.remove(listener);
    }

    private void notifyCategorySelectionChanged() {
        for (CategorySelectionListener listener : categoryListeners) {
            listener.onCategorySelectionChanged(values[1]);
        }
    }

    private void notifyBookLanguageSelectionChanged() {
        for (BookLanguageSelectionListener listener : bookLanguageListeners) {
            listener.onBookLanguageSelectionChanged(values[0]);
        }
    }

    // Interfaces for observers

    /**
     * Listener interface for category selection changes.
     */
    interface CategorySelectionListener {
        /**
         * Called when the category selection changes.
         *
         * @param category The new selected category.
         */
        void onCategorySelectionChanged(int category);
    }

    /**
     * Listener interface for book language selection changes.
     */
    interface BookLanguageSelectionListener {
        /**
         * Called when the book language selection changes.
         *
         * @param language The new selected language.
         */
        void onBookLanguageSelectionChanged(int language);
    }
}
