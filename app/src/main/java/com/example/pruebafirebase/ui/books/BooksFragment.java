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

        /*if(0 < books.size()){
            title.setText("Reading now");
            layout1 = root.findViewById(R.id.BookLayout1);
            layout1.setVisibility(View.VISIBLE);
            title1 = root.findViewById(R.id.tvBook1);
            author1 = root.findViewById(R.id.tvAuthor1);
            year1 = root.findViewById(R.id.tvYear1);
            title1.setText(books.get(0).title);
            author1.setText(books.get(0).author);
            year1.setText(books.get(0).year);
        }

        if(1 < books.size()){
            layout2 = root.findViewById(R.id.BookLayout2);
            layout2.setVisibility(View.VISIBLE);
            title2 = root.findViewById(R.id.tvBook2);
            author2 = root.findViewById(R.id.tvAuthor2);
            year2 = root.findViewById(R.id.tvYear2);
            title2.setText(books.get(1).title);
            author2.setText(books.get(1).author);
            year2.setText(books.get(1).year);
        }

        if(2 < books.size()){
            layout3 = root.findViewById(R.id.BookLayout3);
            layout3.setVisibility(View.VISIBLE);
            title3 = root.findViewById(R.id.tvBook3);
            author3 = root.findViewById(R.id.tvAuthor3);
            year3 = root.findViewById(R.id.tvYear3);
            title3.setText(books.get(2).title);
            author3.setText(books.get(2).author);
            year3.setText(books.get(2).year);
        }

         */

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