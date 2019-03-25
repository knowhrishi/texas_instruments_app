package com.example.docpat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.docpat.Doctor.DoctorLogin;
import com.example.docpat.Doctor.DoctorMain;
import com.example.docpat.Doctor.DoctorRegister;
import com.example.docpat.Patient.PatientLogin;
import com.example.docpat.Patient.PatientRegister;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

   // ImageView doctor, patient;
    private FirebaseAuth auth;
    CardView doctor, patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctor = (CardView) findViewById(R.id.card_view_doctor);
        patient = (CardView) findViewById(R.id.card_view_patient);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //startActivity(new Intent(MainActivity.this, DoctorMain.class));
            Toast.makeText(this, "CURRENT USER: "+auth.getCurrentUser().toString(), Toast.LENGTH_SHORT).show();
            finish();
        }

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DoctorLogin.class));
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PatientLogin.class));
            }
        });
    }
}
