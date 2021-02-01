package com.example.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pruebafirebase.R;
import com.example.pruebafirebase.ui.books.BooksViewModel;
import com.example.pruebafirebase.ui.books.misLibrosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.ArrayList;

public class BookHistory extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    ArrayList<Book> books;

    private String vUserId;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_history);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        vUserId = user.getUid();

        books = new ArrayList<>();

        mRef = database.getReference("Usuarios/" + vUserId + "/misBooks");
        Query refWithQuery = mRef.orderByChild("state");

        BookHistoryAdapter adapter = new BookHistoryAdapter(books, this);

        refWithQuery.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                books.add(book);
                Log.wtf("HOLA", book.title);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                books.add(book);
                Log.wtf("HOLA", book.title);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE ERROR", "Failed to read value.", error.toException());
            }
        });

        recyclerView = findViewById(R.id.recyclerViewHistory);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Book thisBook = books.get(pos);
        Intent intent = new Intent(this, BookShowcase.class);

        intent.putExtra("Title", thisBook.title);

        intent.putExtra("Author", thisBook.author);

        intent.putExtra("Year", thisBook.year);

        intent.putExtra("ImgId", thisBook.imgId);

        intent.putExtra("Subject", thisBook.subject);

        intent.putExtra("Language", thisBook.language);

        intent.putExtra("AmazonId", thisBook.amazonId);



        intent.putExtra("stateVal", thisBook.state);

        String stateStr;
        switch(thisBook.state){
            case 0:
                stateStr = "Done Reading";
                break;
            case 1:
                stateStr = "To read";
                break;
            case 2:
                stateStr = "Reading Now";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + thisBook.state);
        }

        intent.putExtra("state", stateStr);
        startActivity(intent);
    }
}