package com.example.bookstoreapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MyAdapterTest {

    @Mock
    Context mockContext;

    private MyAdapter adapter;
    private ArrayList<HashMap<String, Object>> items;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out; // Redirect log output to console for debugging
        mockContext = RuntimeEnvironment.getApplication();
        items = new ArrayList<>();
    }

    @Test
    public void getItemCount_returnsNullItemCount() {
        adapter = new MyAdapter(mockContext, items);
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void getItemCount_returnsCorrectItemCount() {
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("Name", "Book 1");
        item1.put("Author", "Author 1");
        item1.put("Price", "100");
        item1.put("Cover", "https://example.com/cover1.jpg");
        items.add(item1);

        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("Name", "Book 2");
        item2.put("Author", "Author 2");
        item2.put("Price", "200");
        item2.put("Cover", "https://example.com/cover2.jpg");
        items.add(item2);

        adapter = new MyAdapter(mockContext, items);
        assertEquals(2, adapter.getItemCount());
    }

}
