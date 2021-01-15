package com.example.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText name, age, mail, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.editTextName);
        age = findViewById(R.id.editTextAge);
        mail = findViewById(R.id.editTextMail);
        password = findViewById(R.id.editTextPasswordRegister);
    }

    public void registerUser(View v){
        String nameStr = name.getText().toString();
        String ageStr = age.getText().toString();
        String mailStr = mail.getText().toString();
        String passwordStr = password.getText().toString();

        if(nameStr.isEmpty()){
            name.setError("Name is required");
            name.requestFocus(); //Luego ver porque no aparecen signos de admiración
            return;
        }

        if(ageStr.isEmpty()){
            age.setError("Age is required");
            age.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mailStr).matches()){
            mail.setError("Email is required");
            mail.requestFocus();
            return;
        }

        if(passwordStr.isEmpty() || passwordStr.length() < 6){
            password.setError("Password of 6 chars is required");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(mailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Usuario nuevoUsuario = new Usuario(nameStr, ageStr, mailStr);

                    FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(nuevoUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistroActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(RegistroActivity.this, "We could not register the user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegistroActivity.this, "We could not register the user", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}