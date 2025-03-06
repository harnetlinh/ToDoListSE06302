package com.example.todolistse06302;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private TextView txtError;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences; // store login status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        // create instance to manage login status
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtError = findViewById(R.id.txtError);
        btnRegister = findViewById(R.id.btnRegister);

        if (sharedPreferences.getBoolean("isLoggedIn", false))
        {
            txtError.setText("You are already logged in");
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle register
                navigateToRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle login
                loginUser();
            }
        });
    }

    // handle login
    private void loginUser(){
        String email = edtEmail.getText().toString().trim(); // get email and password from input
        String password = edtPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            txtError.setText("Please enter email and password");
            txtError.setVisibility(View.VISIBLE);
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password) // sign in with email and password
                .addOnCompleteListener(this, task -> { // on event checking process is successful
                    if (task.isSuccessful()) { // if login is successful
                        SharedPreferences.Editor editor = sharedPreferences.edit(); // save login status
                        editor.putBoolean("isLoggedIn", true); // edit login status to true (logged in)
                        editor.apply(); // save login status
                        txtError.setText("Login successful");
                        txtError.setVisibility(View.VISIBLE);
                    } else {
                        txtError.setText("Login failed");
                        txtError.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void navigateToHome(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    private void navigateToRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
}