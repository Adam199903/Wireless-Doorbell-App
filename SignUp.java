package com.adam.iotappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.PhantomReference;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,20}" +               //at least 4 characters
                    "$");



    //get data variables from layout
    TextInputLayout fullname, username, email, password, phoneNo;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_up_design);


        //Hooks to get data from layout
        fullname = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        phoneNo = findViewById(R.id.phoneNo);

    }



    //calls to the login screen
    public void CallSignNo(View view){

        if (!validateFullName() | !validateEmail() | !validatePassword() | !validateUsername() | !validatePhoneNo()){
            return;
        }

        //writes message to firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //puts all values in strings
        String Name = fullname.getEditText().getText().toString();
        String Username = username.getEditText().getText().toString();
        String Email = email.getEditText().getText().toString();
        String Password = password.getEditText().getText().toString();
        String PhoneNo = phoneNo.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(Name, Username, Email, Password, PhoneNo);
        reference.child(Username).setValue(helperClass);

        Intent intent = new Intent (SignUp.this, Login.class);
        startActivity(intent);


    }

    //calls back to the login screen
    public void bckLogin(View view){

        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);


    }




    /*---------------------------Registration validation functions-------------------------*/
    private Boolean validateFullName() {
        String val = fullname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullname.setError("Field cannot be empty");
            return false;

        }
        else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        }
        else if (val.length()>20){
            username.setError("Username too long");
            return false;
        }
        else if (!val.matches(checkspaces)){
            username.setError("Spaces are not allowed");
            return false;
        }

        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();

        if(!val.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
        else {
            email.setError("Invalid Email");
            return false;
        }

    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (!val.isEmpty() && PASSWORD_PATTERN.matcher(val).matches()) {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
        else {
            password.setError("Password to weak, please include, one uppercase letter, one number, and a minimum four characters");
            return false;
        }

    }

    private boolean validatePhoneNo(){
        String val = phoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            phoneNo.setError("Field cannot be empty");
            return false;

        }
        else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            return true;
        }
    }

    /*-------------------------------------------------------------------------------------*/





}