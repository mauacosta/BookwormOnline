package com.example.pruebafirebase.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebafirebase.BookHistory;
import com.example.pruebafirebase.HomeActivity;
import com.example.pruebafirebase.MainActivity;
import com.example.pruebafirebase.R;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {

    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_NAME = "nombre";
    private SettingsViewModel settingsViewModel;
    EditText nombre;
    Button boton, botonLogout, botonHistory;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);


        nombre = root.findViewById(R.id.etChangeName);
        boton = root.findViewById(R.id.btnChangeName);
        botonLogout = root.findViewById(R.id.btnLogout);
        botonHistory = root.findViewById(R.id.btnBookHistory);

        prefs = getActivity().getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_NAME, nombre.getText().toString());
                editor.commit();
            }
        });

        botonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        botonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BookHistory.class);
                startActivity(i);
            }
        });

            return root;

    }


}