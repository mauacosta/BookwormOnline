package com.example.pruebafirebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    private DbHelper db;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";
    private String emailStr;

    public SearchBookAdapter(ArrayList<Book> books, View.OnClickListener listener){
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_element, parent, false);
        db = new DbHelper(parent.getContext());
        prefs = parent.getContext().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        emailStr =  prefs.getString(KEY_MAIL, "@");
        v.setOnClickListener(listener);
        SearchBookViewHolder sbVH = new SearchBookViewHolder(v);

        return sbVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBookViewHolder holder, int position) {
        holder.addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.save(emailStr, books.get(position).title, books.get(position).author, books.get(position).year, books.get(position).imgId,books.get(position).subject, books.get(position).language, books.get(position).amazonId);
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
