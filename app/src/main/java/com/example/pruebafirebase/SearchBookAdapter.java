package com.example.pruebafirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchBookViewHolder> {

    public class SearchBookViewHolder extends RecyclerView.ViewHolder{
        public TextView name, author, year, addBook;

        public SearchBookViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.searchName);
            author = (TextView) itemView.findViewById(R.id.searchAuthor);
            year = (TextView) itemView.findViewById(R.id.searchYear);
            addBook = (TextView) itemView.findViewById(R.id.addSearchBtn);
        }
    }

    private ArrayList<Book> books;
    private View.OnClickListener listener;
    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";
    private String emailStr;
    private Context context;

    private String vUserId;
    public FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;


    public SearchBookAdapter(ArrayList<Book> books, View.OnClickListener listener){
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_element, parent, false);
        context = parent.getContext();
        prefs = parent.getContext().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        emailStr =  prefs.getString(KEY_MAIL, "@");
        v.setOnClickListener(listener);
        SearchBookViewHolder sbVH = new SearchBookViewHolder(v);

        return sbVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBookViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        vUserId = user.getUid();

        holder.addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book libro = new Book(books.get(position).title, books.get(position).author, books.get(position).year, books.get(position).imgId, books.get(position).subject,books.get(position).language, books.get(position).amazonId, 1);

                ref = database.getReference("Usuarios/"+ vUserId);
                ref.child("misBooks/" + libro.title).setValue(libro);

                Toast.makeText(context, libro.title + " Added To Books To Read", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, HomeActivity.class);
                context.startActivity(i);


            }
        });

        holder.name.setText(books.get(position).title);
        holder.author.setText(books.get(position).author);
        holder.year.setText(books.get(position).year);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


}
