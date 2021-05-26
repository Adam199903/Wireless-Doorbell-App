package com.adam.iotappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {


    TextInputLayout username, password;
    TextInputEditText passwordEditText, usernameEditText;
    CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_design);


        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        rememberMe = findViewById(R.id.remember_me);
        passwordEditText = findViewById(R.id.password_edit_text);
        usernameEditText = findViewById(R.id.username_edit_text);


        }




    private boolean validateFields() {
        String val = username.getEditText().getText().toString();
        String val1 = password.getEditText().getText().toString();

        if (val.isEmpty() || val1.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            password.setError(null);
            username.setErrorEnabled(false);
            password.setErrorEnabled(false);
            return true;
        }

    }

    public void callMainScreen(View view) {
        //validate username and password
        if (!validateFields()) {
            return;
        } else confirmUser();

    }

    private void confirmUser() {
        //get data from user entered fields to later compare
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim(); 

        //database check, goes to node "user" goes to child name "username" then checks if its equal to entered data userEnteredUsername
        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    username.setError(null);
                    username.setErrorEnabled(false);

                    //goes to the users entered username
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    //if the password matches it sets the error to null
                    if (passwordFromDB.equals(userEnteredPassword)) {

                        password.setError(null);
                        password.setErrorEnabled(false);

                        // gets the username from firebase and puts it into a string
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        //makes an intent and places the string into the next class for main screen to be able to access the username
                        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                        intent.putExtra("username", usernameFromDB);
                        startActivity(intent);


                    }
                    //lets the user know they typed the wrong data
                    else {
                        password.setError("wrong password");
                        password.requestFocus();
                    }
                } else {
                    username.setError("no such user exists");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callSignUp(View view) {

        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }


}