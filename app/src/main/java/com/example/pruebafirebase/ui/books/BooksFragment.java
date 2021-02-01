package com.example.pruebafirebase.ui.books;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafirebase.AddBook;
import com.example.pruebafirebase.Book;
import com.example.pruebafirebase.BookShowcase;
import com.example.pruebafirebase.DbHelper;
import com.example.pruebafirebase.R;
import com.example.pruebafirebase.SearchBookAdapter;

import java.util.ArrayList;

public class BooksFragment extends Fragment implements View.OnClickListener{

    private BooksViewModel booksViewModel;
    TextView title;
    private RecyclerView recyclerView;
    //TextView title, title1, title2, title3, author1, author2, author3, year1, year2,year3;

    //ConstraintLayout layout1, layout2, layout3;

    DbHelper db;
    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";
    ArrayList<Book> books;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                new ViewModelProvider(this).get(BooksViewModel.class);

        View root = inflater.inflate(R.layout.fragment_books, container, false);
        DbHelper db = new DbHelper(this.getActivity());
        prefs = getActivity().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        String mailStr = prefs.getString(KEY_MAIL, "@");

        title = root.findViewById(R.id.tvReadingTitle);
        //title.setText("Add your first book -->");

        books = new ArrayList<Book>();
        books = db.search(mailStr);

        recyclerView = root.findViewById(R.id.recyclerViewMyBooks);

        misLibrosAdapter adapter = new misLibrosAdapter(books, this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        //books = db.search(mailStr);


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

        intent.putExtra("state", "To Read");

        intent.putExtra("stateVal", 1);

        startActivity(intent);
    }
}