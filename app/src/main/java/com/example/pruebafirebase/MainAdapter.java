package com.example.pruebafirebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafirebase.ui.books.misLibrosAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>  {

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public TextView name, author, year;
        public ImageView cover;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.bookCover);
            name = (TextView) itemView.findViewById(R.id.bookTitle);
            author = (TextView) itemView.findViewById(R.id.tvAuthor);
            year = (TextView) itemView.findViewById(R.id.tvYear);
        }
    }

    private ArrayList<Book> books;
    private View.OnClickListener listener;

    public MainAdapter(ArrayList<Book> books, View.OnClickListener listener){
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        v.setOnClickListener(listener);
        MainViewHolder sbVH = new MainViewHolder(v);
        return sbVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.name.setText(books.get(position).title);
        holder.author.setText(books.get(position).author);
        holder.year.setText(books.get(position).year);
        if(!books.get(position).imgId.equals("-")){
            try {
                GetContent getBitmap = new GetContent();
                Bitmap bitmap = getBitmap.execute("http://covers.openlibrary.org/b/id/" + books.get(position).imgId + ".jpg").get();
                holder.cover.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Log.wtf("IMAGE", "Image doesn't have cover");
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
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