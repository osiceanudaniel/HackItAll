package com.example.osi.hackitallapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    private EditText retypePassEditText;
    private CheckBox showPass;
    private Button signupBtn;

    private ProgressDialog registrationProgress;

    private FirebaseAuth authUser;
    private FirebaseAuth.AuthStateListener authUserListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get auth user reference
        authUser = FirebaseAuth.getInstance();
        authUserListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // if user is logged in, log him out and go to the login page
                if(firebaseAuth.getCurrentUser() != null) {

                    // go to main activity
                    Intent loginIntent = new Intent(RegisterActivity.this,
                            MainPageActivity.class);
                    // check if activity login was already running
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        // get reference from xml objects
        emailEditText = (EditText) findViewById(R.id.registerEmailTextField);
        passEditText = (EditText) findViewById(R.id.registerPasswordTextField);
        retypePassEditText = (EditText) findViewById(R.id.registerPasswordValidationTextField);
        showPass = (CheckBox) findViewById(R.id.checkRegisterShowPassword);
        signupBtn = (Button) findViewById(R.id.registerBtn);

        // checkBox functionality
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    passEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    retypePassEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    retypePassEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // initialize text
        emailEditText.setText("");
        passEditText.setText("");
        retypePassEditText.setText("");

        // intialize register progress dialog
        registrationProgress = new ProgressDialog(RegisterActivity.this);
        registrationProgress.setTitle(this.getString(R.string.registrationProgressTitle));
        registrationProgress.setMessage(this.getString(R.string.registrationProgressMessage));

        // listen if the user taps register button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                String password = passEditText.getText().toString();

                // check if the user completed all fields
                if(LoginActivity.checkEditText(email, password)) {
                    if (LoginActivity.isValid(email)) {
                            if (password.length() >= 6) {
                                if (passEditText.getText().toString().equals(
                                        retypePassEditText.getText().toString())) {
                                    registrationProgress.show();

                                    // register the user
                                    registerUser(email, password);
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            getString(R.string.toastPassNotMatch), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this,
                                        getString(R.string.toastPassShort), Toast.LENGTH_SHORT).show();
                            }
                    } else {
                        Toast.makeText(RegisterActivity.this,
                                getString(R.string.toastNotEmailAddress), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(RegisterActivity.this, R.string.toastEmptyText,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // register the user to firebase
    private void registerUser(String email, String password) {
        authUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, R.string.toastSuccReg,
                            Toast.LENGTH_SHORT).show();

                    // dismiss the progress bar
                    registrationProgress.dismiss();
                } else {

                    // dismiss the progress bar
                    registrationProgress.dismiss();

                    Toast.makeText(RegisterActivity.this, R.string.registrationFailed,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        authUser.addAuthStateListener(authUserListener);
    }
}
