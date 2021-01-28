package com.example.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

            for(int i = 0; (i<data.length());i++){
                JSONObject temp = data.getJSONObject(i);
                String thisTitle, thisAuthor, thisYear;
                String jSONTitleValue = temp.get("title").toString();
                String jSONAuthorValue = temp.getJSONArray("author_name").get(0).toString();
                String jSONYearValue = thisYear = temp.getJSONArray("publish_year").get(0).toString();

                if(jSONTitleValue != null){
                    thisTitle = jSONTitleValue;
                }else{
                    thisTitle = "";
                }

                if(jSONAuthorValue != null){
                    thisAuthor = jSONAuthorValue;
                }else{
                    thisAuthor = "";
                }

                if(jSONYearValue != null){
                    thisYear = jSONYearValue;
                }else{
                    thisYear = "";
                }

                Book thisBook = new Book(thisTitle, thisAuthor, thisYear, "","","","");
                books.add(thisBook);
                Log.wtf("REQUEST", books.get(i).year);

                SearchBookAdapter adapter = new SearchBookAdapter(books, this);
                GridLayoutManager glm = new GridLayoutManager(this, 2);
                glm.setOrientation(GridLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(glm);
                recyclerView.setAdapter(adapter);


            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}