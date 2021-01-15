package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SearchBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
    }

    public void goToCustomBook(View v){
        Intent i = new Intent(this, AddBook.class);
        startActivity(i);
    }
}