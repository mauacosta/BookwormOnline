package com.example.pruebafirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafirebase.ui.books.misLibrosAdapter;

import java.util.ArrayList;

public class BookHistoryAdapter extends RecyclerView.Adapter<BookHistoryAdapter.BookHistoryViewHolder> {

    public class BookHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView element;


        public BookHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            element = (TextView) itemView.findViewById(R.id.historyTV);
        }
    }

    private ArrayList<Book> books;
    private View.OnClickListener listener;

    public BookHistoryAdapter(ArrayList<Book> books, View.OnClickListener listener){
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);
        v.setOnClickListener(listener);
        BookHistoryAdapter.BookHistoryViewHolder bhVH = new BookHistoryAdapter.BookHistoryViewHolder(v);
        return bhVH;
    }

    @Override
    public void onBindViewHolder(@NonNull BookHistoryViewHolder holder, int position) {
        holder.element.setText("-" + books.get(position).title + ", " +books.get(position).author);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


}
