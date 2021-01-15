package com.example.pruebafirebase.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebafirebase.R;

public class BooksFragment extends Fragment {

    private BooksViewModel booksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                new ViewModelProvider(this).get(BooksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_books, container, false);
        return root;
    }

}