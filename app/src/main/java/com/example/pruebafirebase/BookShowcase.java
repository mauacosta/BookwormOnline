package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BookShowcase extends AppCompatActivity {

    private String title, author, year, subject, language, state;
    private TextView titleTV, authorTV, yearTV, subjectTV, langTV;
    private Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_showcase);
        titleTV = findViewById(R.id.showTitle);
        authorTV = findViewById(R.id.showAuthor);
        yearTV = findViewById(R.id.showYear);
        subjectTV = findViewById(R.id.showSubject);
        langTV = findViewById(R.id.showLanguage);
        bookButton = findViewById(R.id.bookBtn);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("Title");
        author = extras.getString("Author");
        year = extras.getString("Year");
        subject = extras.getString("Subject");
        language = extras.getString("Language");
        state = extras.getString("state");

        titleTV.setText(title);
        authorTV.setText(author);
        yearTV.setText(year);
        subjectTV.setText("Subject " + subject);
        langTV.setText("Language: " + language);
        bookButton.setText(state);
    }
}