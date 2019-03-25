package com.example.docpat.Patient;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.docpat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.docpat.Patient.PatientRegister.KEY_ADDRESS;
import static com.example.docpat.Patient.PatientRegister.KEY_DOCTOR;
import static com.example.docpat.Patient.PatientRegister.KEY_DOB;
import static com.example.docpat.Patient.PatientRegister.KEY_EMAIL;
import static com.example.docpat.Patient.PatientRegister.KEY_FIRSTNAME;
import static com.example.docpat.Patient.PatientRegister.KEY_GENDER;
import static com.example.docpat.Patient.PatientRegister.KEY_LASTNAME;
import static com.example.docpat.Patient.PatientRegister.KEY_PHONE;
import static com.example.docpat.Patient.PatientRegister.KEY_UID;

public class PatientProfile extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView textViewFirstName, textViewLastName,textViewPhone, textViewGender, textViewEmail, textViewAddress, textViewDoctor, textViewDOB, textViewUID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private DocumentReference noteRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");


        auth = FirebaseAuth.getInstance();

        textViewFirstName = (TextView) findViewById(R.id.first_name);
        textViewLastName = (TextView) findViewById(R.id.last_name);
        textViewPhone = (TextView) findViewById(R.id.phone_number);
        textViewGender = (TextView) findViewById(R.id.gender);
        textViewDOB = (TextView) findViewById(R.id.DOB);
        textViewEmail = (TextView) findViewById(R.id.email);
        textViewAddress = (TextView) findViewById(R.id.address);

        textViewDoctor = (TextView) findViewById(R.id.doctor);
        textViewUID = (TextView) findViewById(R.id.aadhar_no);


        noteRef = db.collection("Patient").document(auth.getUid());
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        if(documentSnapshot.exists()){
                            String first_name = documentSnapshot.getString(KEY_FIRSTNAME);
                            String last_name = documentSnapshot.getString(KEY_LASTNAME);
                            String phone_number = documentSnapshot.getString(KEY_PHONE);
                            String gender = documentSnapshot.getString(KEY_GENDER);
                            String dob = documentSnapshot.getString(KEY_DOB);
                            String email = documentSnapshot.getString(KEY_EMAIL);
                            String address = documentSnapshot.getString(KEY_ADDRESS);
                            String degree = documentSnapshot.getString(KEY_DOCTOR);
                            String uid = documentSnapshot.getString(KEY_UID);

                            textViewFirstName.setText(first_name);
                            textViewLastName.setText(last_name);
                            textViewPhone.setText(phone_number);
                            textViewGender.setText(gender);
                            textViewEmail.setText(email);
                            textViewAddress.setText(address);
                            textViewDoctor.setText(degree);
                            textViewDOB.setText(dob);
                            textViewUID.setText(uid);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
