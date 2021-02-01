package com.example.pruebafirebase.ui.books;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafirebase.AddBook;
import com.example.pruebafirebase.Book;
import com.example.pruebafirebase.BookHistoryAdapter;
import com.example.pruebafirebase.BookShowcase;
import com.example.pruebafirebase.DbHelper;
import com.example.pruebafirebase.R;
import com.example.pruebafirebase.SearchBookAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class BooksFragment extends Fragment implements View.OnClickListener{

    private BooksViewModel booksViewModel;
    private RecyclerView recyclerView;
    ArrayList<Book> books;

    private String vUserId;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    DatabaseReference mRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                new ViewModelProvider(this).get(BooksViewModel.class);

        View root = inflater.inflate(R.layout.fragment_books, container, false);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        vUserId = user.getUid();

        books = new ArrayList<>();

        mRef = database.getReference("Usuarios/" + vUserId + "/misBooks");
        Query refWithQuery = mRef.orderByChild("state").equalTo(1);

        misLibrosAdapter adapter = new misLibrosAdapter(books, this);

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


        recyclerView = root.findViewById(R.id.recyclerViewMyBooks);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Book thisBook = books.get(pos);
        Intent intent = new Intent(this.getActivity(), BookShowcase.class);

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