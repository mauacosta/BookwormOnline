package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookShowcase extends AppCompatActivity {

    private String emailStr, title, author, year, subject, imgId, language, state, amazonId;
    private int stateVal;
    private TextView titleTV, authorTV, yearTV, subjectTV, langTV, stateTV;
    private Button bookButton;

    public SharedPreferences prefs;
    private DbHelper db;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";

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
        stateTV = findViewById(R.id.showState);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("Title");
        author = extras.getString("Author");
        year = extras.getString("Year");
        subject = extras.getString("Subject");
        language = extras.getString("Language");
        imgId = extras.getString("ImgId");
        state = extras.getString("state");
        amazonId = extras.getString("AmazonId");
        stateVal = extras.getInt("stateVal");

        if(stateVal == 1){
            bookButton.setVisibility(View.GONE);
        }else if(stateVal == 0){
            stateTV.setVisibility(View.GONE);
        }

        titleTV.setText(title);
        authorTV.setText(author);
        yearTV.setText(year);
        subjectTV.setText("Subject: " + subject);
        langTV.setText("Language: " + language);
        bookButton.setText("Add book");
        stateTV.setText("State: " + state);

        db = new DbHelper(this);
        prefs = getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        emailStr =  prefs.getString(KEY_MAIL, "@");

    }

    public void addBook(View v){
        db.save(emailStr, title, author, year, imgId, subject, language, amazonId);
        Intent i = new Intent(this, HomeActivity.class);
        Toast.makeText(this, title + " Added", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}