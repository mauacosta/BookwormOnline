package com.example.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView register;
    EditText mail, password;
    private FirebaseAuth mAuth;

    public SharedPreferences prefs;
    private static final String ARCHIVO_PREFS = "misPrefs";
    private static final String KEY_NAME = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.textViewRegister);
        mail = findViewById(R.id.editTextUser);
        password = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE);
    }

    public void abrirRegistro (View v){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }

    public void hacerLogin(View v){
        String mailStr = mail.getText().toString();
        String passwordStr = password.getText().toString();

        if(mailStr.isEmpty()){
            mail.setError("Mail is required");
            Toast.makeText(MainActivity.this, "Invalid mail", Toast.LENGTH_SHORT).show();
            mail.requestFocus(); //Luego ver porque no aparecen signos de admiración
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mailStr).matches()){
            mail.setError("Email is not valid");
            Toast.makeText(MainActivity.this, "Invalid mail", Toast.LENGTH_SHORT).show();
            mail.requestFocus();
            return;
        }

        if(passwordStr.isEmpty() || passwordStr.length() < 6){
            password.setError("Password of 6 chars is required");
            Toast.makeText(MainActivity.this, "Invalid mail", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(mailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_NAME, mailStr);
                    editor.apply();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not succesfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

// La forma en la que aprendimos fue por medio de este documento https://www.youtube.com/watch?v=Z-RE1QuUWPg