package com.example.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchBook extends AppCompatActivity implements Handler.Callback, View.OnClickListener {

    private Handler handler;
    private ArrayList<Book> books;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        handler = new Handler(Looper.getMainLooper(), this);
        books = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerSearch);
    }

    public void goToCustomBook(View v){
        Intent i = new Intent(this, AddBook.class);
        startActivity(i);
    }

    public void doRequest(View v){
        books.clear();
        EditText searchInput = findViewById(R.id.searchET);
        String query = searchInput.getText().toString();
        String querySendable = query.replaceAll("\\s", "+");
        SearchBookRequest request = new SearchBookRequest("http://openlibrary.org/search.json?q=" + querySendable, handler);
        request.start();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        try{

            JSONArray data = (JSONArray) msg.obj;

            for(int i = 0; ((i < 10) && (i < data.length())); i++){
                JSONObject temp = data.getJSONObject(i);
                String thisTitle, thisAuthor, thisYear, thisImg, thisSubject, thisLang, thisAmzn;
                try {
                    thisTitle = temp.get("title").toString();
                }catch(Exception e){
                    thisTitle = "";
                }

                try{
                    thisAuthor = temp.getJSONArray("author_name").get(0).toString();
                }catch(Exception e){
                    thisAuthor = "";
                }

                try{
                    thisYear = temp.getJSONArray("publish_year").get(0).toString();
                }catch(Exception e){
                    thisYear = "";
                }

                try{
                    thisImg = temp.get("cover_i").toString();
                }catch(Exception e){
                    thisImg = "";
                }

                try{
                    thisSubject = temp.getJSONArray("subject").get(0).toString();
                }catch(Exception e){
                    thisSubject = "";
                }

                try{
                    thisLang = temp.getJSONArray("language").get(0).toString();
                }catch(Exception e){
                    thisLang = "";
                }

                try{
                    thisAmzn = temp.getJSONArray("id_amazon").get(0).toString();
                }catch(Exception e){
                    thisAmzn = "";
                }

                Book thisBook = new Book(thisTitle, thisAuthor, thisYear, thisImg,thisSubject,thisLang,thisAmzn);
                books.add(thisBook);


                SearchBookAdapter adapter = new SearchBookAdapter(books, this);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(GridLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);


            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
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

        intent.putExtra("state", "Add Book");


        startActivity(intent);
    }
}