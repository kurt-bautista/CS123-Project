package com.grpd.secb.mngr;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Create a new account");

        emailEditText = (EditText)findViewById(R.id.registerEmailEditText);
        passwordEditText = (EditText)findViewById(R.id.registerPasswordEditText);
        repeatPasswordEditText = (EditText)findViewById(R.id.registerRepeatPasswordEditText);

        mAuth = FirebaseAuth.getInstance();
    }

    public void createAccount(View v) {
        if(!validateForm()) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Create account?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(!task.isSuccessful()) Toast.makeText(RegisterActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                                        else {
                                            finish();
                                        }
                                    }
                                });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setTitle("Confirm");
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validateForm() {
        boolean valid = true;

        String emailAddress = emailEditText.getText().toString();
        if (TextUtils.isEmpty(emailAddress)) {
            emailEditText.setError("Required");
            valid = false;
        } else {
            emailEditText.setError(null);
        }


        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        String password2 = repeatPasswordEditText.getText().toString();
        if (!password.equals(password2)) {
            repeatPasswordEditText.setError("Passwords do not match");
            valid = false;
        } else {
            repeatPasswordEditText.setError(null);
        }

        return valid;
    }
}
