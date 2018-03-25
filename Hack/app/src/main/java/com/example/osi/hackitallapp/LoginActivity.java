package com.example.osi.hackitallapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    private TextView registerLink;
    private Button loginBtn;
    private CheckBox showPass;

    private ProgressDialog progressLogin;

    private FirebaseAuth authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get the user reference
        authUser = FirebaseAuth.getInstance();

        // get xml objects reference
        emailEditText = (EditText) findViewById(R.id.loginEmailTextField);
        passEditText = (EditText) findViewById(R.id.loginPasswordTextField);
        registerLink = (TextView) findViewById(R.id.loginRegisterLinkText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        showPass = (CheckBox) findViewById(R.id.checkLoginShowPassword);

        // initialize the progress dialog
        progressLogin = new ProgressDialog(LoginActivity.this);
        progressLogin.setTitle(R.string.loginProgressTitle);

        // checkBox functionality
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    passEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // initialize pass text field to be empty
        passEditText.setText("");

        // listen if the user taps the register text
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start the register activity
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);

                // see if the activity was already open
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });

        //listen if the user taps login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                String password = passEditText.getText().toString();

                // check if the user entered something
                if (checkEditText(email, password)) {

                    if (isValid(email)) {
                        progressLogin.show();

                        // login the user
                        loginUser(email, password);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                getString(R.string.toastNotEmailAddress), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            getString(R.string.toastEmptyText), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // check if username and password fields are not empty
    public static boolean checkEditText(String email, String password) {

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return false;
        }

        return true;
    }

    // user login code
    private void loginUser(String email, String password) {
        authUser.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // if login was successful
                if(task.isSuccessful()) {

                    // redirect the user
                    Intent mainPageIntent = new Intent(LoginActivity.this,
                            MainPageActivity.class);
                    mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainPageIntent);

                    // dismiss the progress bar
                    progressLogin.dismiss();
                } else {

                    // dismiss the progress bar
                    progressLogin.dismiss();
                    passEditText.setText("");

                    Toast.makeText(LoginActivity.this, R.string.loginFailed,
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // exit app when back button pressed
    public void onBackPressed(){
        Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exit);
    }

    // check if the user entered a valid email address
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
