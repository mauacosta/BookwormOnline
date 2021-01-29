package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddBook extends AppCompatActivity {

    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";
    private EditText etTitle, etAuthor, etYear;
    String emailStr;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        prefs = getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        emailStr =  prefs.getString(KEY_MAIL, "@");
        db = new DbHelper(this);
    }

    public void saveBook(View view){
        etTitle = findViewById(R.id.etBookName);
        etAuthor = findViewById(R.id.etAuthorName);
        etYear = findViewById(R.id.etYear);
        String strTitle = etTitle.getText().toString();
        String strAuthor = etAuthor.getText().toString();
        String strYear = etYear.getText().toString();
        db.save(emailStr, strTitle, strAuthor, strYear, "123", "123", "123", "123");
        Toast.makeText(AddBook.this, strTitle + " Agregado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddBook.this, HomeActivity.class);
        startActivity(i);
    }

}