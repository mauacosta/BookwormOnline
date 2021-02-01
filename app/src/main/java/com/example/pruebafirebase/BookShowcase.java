package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BookShowcase extends AppCompatActivity {

    private String emailStr, title, author, year, subject, imgId, language, state, amazonId;
    private int stateVal;
    private TextView titleTV, authorTV, yearTV, subjectTV, langTV, stateTV,amazonTV, stateHint;
    Spinner spinner;
    private ImageView bookImg;
    private Button bookButton, applyButton;

    public SharedPreferences prefs;
    private DbHelper db;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";

    private String vUserId;
    public FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

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
        bookImg = findViewById(R.id.showImage);
        amazonTV = findViewById(R.id.amazonBtn);
        applyButton = findViewById(R.id.applyBtn);
        spinner = (Spinner) findViewById(R.id.statusSpinner);
        stateHint = findViewById(R.id.statusHint);

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

        if((stateVal >= 0)){
            bookButton.setVisibility(View.GONE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.statusChoice, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }else{
            stateTV.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            applyButton.setVisibility(View.GONE);
            stateHint.setVisibility(View.GONE);
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

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        vUserId = user.getUid();

        if(!imgId.equals("-")){
            try {
                GetContent getBitmap = new GetContent();
                Bitmap bitmap = getBitmap.execute("http://covers.openlibrary.org/b/id/" + imgId + ".jpg").get();
                bookImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Log.wtf("IMAGE", "Image doesn't have cover");
        }

        if(amazonId.equals("-")){
            amazonTV.setVisibility(View.GONE);
        }
    }

    public void addBook(View v){
        db.save(emailStr, title, author, year, imgId, subject, language, amazonId);
        Intent i = new Intent(this, HomeActivity.class);
        Toast.makeText(this, title + " Added", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    public void writeBook(View v){

        Book libro = new Book(title, author, year, imgId, subject, language, amazonId, 1);

        ref = database.getReference("Usuarios/"+ vUserId);
        ref.child("misBooks/" + libro.title).setValue(libro);

        Toast.makeText(this, libro.title + " Added to *Books To Read*", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    class GetContent extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
            } catch (IOException e) {
                Log.wtf("IMAGE", "Error with cover");
            }
            return bitmap;
        }

    }
}