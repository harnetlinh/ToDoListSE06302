package com.example.todolistse06302;

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

import com.example.todolistse06302.database.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.AbstractList;
import java.util.List;

public class Register extends AppCompatActivity {

    private EditText edtEmailRegister, edtPasswordRegister;
    private Button btnSubmitRegister;

    private DatabaseHelper dbHelper;

    private FirebaseAuth mAuth;

    private TextView txtMessage;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);
        dbHelper = new DatabaseHelper(this);
        txtMessage = findViewById(R.id.txtMessage);

        btnSubmitRegister.setOnClickListener(v -> {
            String email = edtEmailRegister.getText().toString();
            String pass = edtPasswordRegister.getText().toString();
            long id = dbHelper.addUser(email, pass);
            if (id > 0){
                txtMessage.setText("Success " + id);
                showUser();
            }else{
                txtMessage.setText("Fail");
            }
            txtMessage.setVisibility(View.VISIBLE);
        });

    }

    private void showUser(){
        List<String[]> usersString = dbHelper.getUsers();
        int lengthUser = usersString.size() - 1;
        txtMessage.setText( lengthUser + " users have been created. And " +
                usersString.get(lengthUser)[0] + " has just been created");
    }
}