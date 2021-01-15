package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebafirebase.ui.books.BooksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.util.ArrayList;
import com.example.pruebafirebase.R;

public class HomeActivity extends AppCompatActivity {


    DbHelper db;
    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_MAIL = "email";
    ArrayList<Book> books;
    BooksFragment fragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_books, R.id.navigation_settings)
                .build();
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        /*
        fragmento = new BooksFragment();
        DbHelper db = new DbHelper(this);
        prefs = getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
        String mailStr = prefs.getString(KEY_MAIL, "@");


        books = new ArrayList<Book>();
        books = db.search(mailStr);

        if(0 < books.size()){
            fragmento.insertBookData1(books.get(0).title, books.get(0).author, books.get(0).year);
        }

        if(1 < books.size()){
            fragmento.insertBookData2(books.get(1).title, books.get(1).author, books.get(1).year);
        }

        if(2 < books.size()){
            fragmento.insertBookData3(books.get(2).title, books.get(2).author, books.get(2).year);
        }
        */
    }

    public void goToAddBook(View v){
        Intent i = new Intent(this, SearchBook.class);
        startActivity(i);
    }

}