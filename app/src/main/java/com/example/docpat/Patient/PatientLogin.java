package com.example.docpat.Patient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.docpat.Doctor.DoctorLogin;
import com.example.docpat.Doctor.DoctorMain;
import com.example.docpat.MainActivity;
import com.example.docpat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientLogin extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(PatientLogin.this, PatientMain.class));
            finish();
        }

        editTextEmail      = (EditText) findViewById(R.id.email);
        editTextPassword   = (EditText) findViewById(R.id.password);
        progressBar        = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup          = (Button) findViewById(R.id.btn_signup);
        btnLogin           = (Button) findViewById(R.id.btn_login);
        btnReset           = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientLogin.this, PatientRegister.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(PatientLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        editTextPassword.setError("Password should be greater than 6 digits");
                                    }else {
                                        Toast.makeText(PatientLogin.this, "Auth Fail", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(PatientLogin.this, PatientMain.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
