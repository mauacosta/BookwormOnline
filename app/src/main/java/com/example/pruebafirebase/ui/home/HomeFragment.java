package com.example.pruebafirebase.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        prefs = getActivity().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        welcomeText = root.findViewById(R.id.tvHomeTitle);
        mailStr = prefs.getString(KEY_MAIL, "Reader");
        welcomeText.setText("Welcome " + prefs.getString(KEY_NAME, mailStr));

        books = new ArrayList<>();

        books.add(new Book("12 Rules for Life", "Jordan B. Peterson", "2016", "8131760", "PHILOSOPHY / Social", "-", "-", 2));
        recyclerView = root.findViewById(R.id.recyclerMain);

        MainAdapter adapter = new MainAdapter(books, this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

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