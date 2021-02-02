package com.example.pruebafirebase.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafirebase.Book;
import com.example.pruebafirebase.BookShowcase;
import com.example.pruebafirebase.MainAdapter;
import com.example.pruebafirebase.R;
import com.example.pruebafirebase.ui.books.misLibrosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener {

    TextView welcomeText;

    private HomeViewModel homeViewModel;
    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_MAIL = "email";
    private String mailStr;

    private RecyclerView recyclerView;
    ArrayList<Book> books;

    private String vUserId;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    DatabaseReference mRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        prefs = getActivity().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        welcomeText = root.findViewById(R.id.tvHomeTitle);
        mailStr = prefs.getString(KEY_MAIL, "Reader");
        welcomeText.setText("Welcome " + prefs.getString(KEY_NAME, mailStr));

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        vUserId = user.getUid();

        books = new ArrayList<>();
        MainAdapter adapter = new MainAdapter(books, this);


        mRef = database.getReference("Usuarios/" + vUserId + "/misBooks");
        Query refWithQuery = mRef.orderByChild("state").equalTo(2);
        refWithQuery.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                books.add(book);
                Log.wtf("HOLA", book.title);
                welcomeText.setText("Welcome " + prefs.getString(KEY_NAME, mailStr));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                books.add(book);
                Log.wtf("HOLA", book.title);
                welcomeText.setText("Welcome " + prefs.getString(KEY_NAME, mailStr));
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


        recyclerView = root.findViewById(R.id.recyclerMain);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        if(books.isEmpty()){
            welcomeText.setText("Welcome " + prefs.getString(KEY_NAME, mailStr) + ". Add Books!");
        }
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