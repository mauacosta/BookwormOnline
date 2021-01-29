package com.example.pruebafirebase.ui.books;
/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class misLibrosAdapter {
}*/


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafirebase.Book;
import com.example.pruebafirebase.R;

import java.util.ArrayList;

public class misLibrosAdapter extends RecyclerView.Adapter<misLibrosAdapter.SearchBookViewHolder> {

    public class SearchBookViewHolder extends RecyclerView.ViewHolder{
        public TextView name, author, year;

        public SearchBookViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.searchName);
            author = (TextView) itemView.findViewById(R.id.searchAuthor);
            year = (TextView) itemView.findViewById(R.id.searchYear);
        }
    }

    private ArrayList<Book> books;
    private View.OnClickListener listener;

    public misLibrosAdapter(ArrayList<Book> books, View.OnClickListener listener){
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_book_row, parent, false);
        v.setOnClickListener(listener);
        SearchBookViewHolder sbVH = new SearchBookViewHolder(v);
        return sbVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBookViewHolder holder, int position) {
        holder.name.setText(books.get(position).title);
        holder.author.setText(books.get(position).author);
        holder.year.setText(books.get(position).year);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


}

