package com.example.pruebafirebase.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebafirebase.R;


public class HomeFragment extends Fragment {

    TextView welcomeText;

    private HomeViewModel homeViewModel;
    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_MAIL = "email";
    private String mailStr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        prefs = getActivity().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);

        welcomeText = root.findViewById(R.id.tvHomeTitle);

        mailStr = prefs.getString(KEY_MAIL, "Reader");
        welcomeText.setText("Welcome " + prefs.getString(KEY_NAME, mailStr));

        return root;
    }

}