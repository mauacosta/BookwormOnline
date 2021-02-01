package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBook extends AppCompatActivity {

    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";
    private EditText etTitle, etAuthor, etYear;
    String emailStr;
    private DbHelper db;

    private String vUserId;
    public FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        prefs = getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        emailStr =  prefs.getString(KEY_MAIL, "@");
        db = new DbHelper(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        vUserId = user.getUid();

    }

    public void saveBook(View view){
        etTitle = findViewById(R.id.etBookName);
        etAuthor = findViewById(R.id.etAuthorName);
        etYear = findViewById(R.id.etYear);
        String strTitle = etTitle.getText().toString();
        String strAuthor = etAuthor.getText().toString();
        String strYear = etYear.getText().toString();
        db.save(emailStr, strTitle, strAuthor, strYear, "-", "-", "-", "-");
        Toast.makeText(AddBook.this, strTitle + " Agregado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddBook.this, HomeActivity.class);
        startActivity(i);
    }


    public void writeNewBook(View v){

        etTitle = findViewById(R.id.etBookName);
        etAuthor = findViewById(R.id.etAuthorName);
        etYear = findViewById(R.id.etYear);

        String strTitle = etTitle.getText().toString();
        String strAuthor = etAuthor.getText().toString();
        String strYear = etYear.getText().toString();

        Book libro = new Book(strTitle, strAuthor, strYear, "-", "-","-", "-", 1);

        ref = database.getReference("Usuarios/"+ vUserId);
        ref.child("misBooks/" + strTitle).setValue(libro);

        Toast.makeText(AddBook.this, strTitle + " is in *Books To Read*", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddBook.this, HomeActivity.class);
        startActivity(i);
    }
}