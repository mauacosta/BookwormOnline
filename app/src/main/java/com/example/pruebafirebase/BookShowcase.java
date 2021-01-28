package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BookShowcase extends AppCompatActivity {

    private String title, author, year, subject, language;
    private TextView titleTV, authorTV, yearTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_showcase);
        titleTV = findViewById(R.id.showTitle);
        authorTV = findViewById(R.id.showAuthor);
        yearTV = findViewById(R.id.showYear);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("Title");
        author = extras.getString("Author");
        year = extras.getString("Year");

        titleTV.setText(title);
        authorTV.setText(author);
        yearTV.setText(year);
    }
}