package com.example.bookstoreapplication;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CheckoutDoneActivity extends BaseActivity {
    private Button button;
    EditText phoneNo;
    FloatingActionButton callbtn;
    static int PERMISSION_code=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_done);

        Button button=(Button) findViewById(R.id.Browse);
        phoneNo=findViewById(R.id.editTextPhone);
        callbtn=findViewById(R.id.callbtn);
        if(ContextCompat.checkSelfPermission(CheckoutDoneActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CheckoutDoneActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_code);
        }
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno=phoneNo.getText().toString();
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneNo));
                startActivity(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CheckoutDoneActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}