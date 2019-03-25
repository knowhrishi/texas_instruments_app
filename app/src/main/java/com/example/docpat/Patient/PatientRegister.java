package com.example.docpat.Patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.docpat.Doctor.DoctorLogin;
import com.example.docpat.Doctor.DoctorMain;
import com.example.docpat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class PatientRegister extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextPhone, editTextEmail, editTextAddress, editTextPassword, editTextDOB, editTextUID;
    ImageButton btnReleaseDatePicker;
    private int mYear, mMonth, mDay;
    private Button btnSignIn, btnSignUp, btnChooseDoctor;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    int selectedRadioButtonID;
    private static final String TAG = "MainActivity";
    String first_name, last_name, phone_number, email_id, password, full_address, selected_doctor, dob, uid;

    ArrayList<String> items = new ArrayList<>();
    SpinnerDialog spinnerDialog;

    public static final String KEY_FIRSTNAME   = "first_name";
    public static final String KEY_LASTNAME    = "last_name";
    public static final String KEY_PHONE       = "phone_number";
    public static final String KEY_DOB         = "dob";
    public static final String KEY_UID         = "aadhar_uid";
    public static final String KEY_EMAIL       = "email_id";
    public static final String KEY_PASSWORD    = "password";
    public static final String KEY_GENDER      = "gender";
    public static final String KEY_DOCTOR      = "doctor";
    public static final String KEY_ADDRESS     = "full_address";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(PatientRegister.this, PatientMain.class));
            finish();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("REGISTER");

        editTextFirstName       = (EditText) findViewById(R.id.first_name);
        editTextLastName        = (EditText) findViewById(R.id.last_name);
        editTextPhone           = (EditText) findViewById(R.id.phone_number);
        editTextEmail           = (EditText) findViewById(R.id.email);
        editTextAddress         = (EditText) findViewById(R.id.address);
        editTextDOB             = (EditText) findViewById(R.id.DOB);
        editTextUID             = (EditText) findViewById(R.id.aadhar_no);
        btnSignIn               = (Button) findViewById(R.id.sign_in_button);
        btnSignUp               = (Button) findViewById(R.id.sign_up_button);
        editTextPassword        = (EditText) findViewById(R.id.password);
        progressBar             = (ProgressBar) findViewById(R.id.progressBar);
        btnChooseDoctor         = (Button)findViewById(R.id.choose_doctor);
        btnReleaseDatePicker    = (ImageButton) findViewById(R.id.btn_release_date);

        radioSexGroup           = (RadioGroup) findViewById(R.id.radioSex);


        btnReleaseDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRegister.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editTextDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        /*items.add("MD(Res), DM");
        items.add("PhD, DPhi");
        items.add("MCM");
        items.add("MMSc, MMedSc");
        items.add("Marshmallow");
        items.add("MM, MMed");
        items.add("MPhil");
        items.add("MS, MSurg, MChir, MCh, ChM, CM");
        items.add("DCM");
        items.add("MSc");
        items.add("DClinSurg");
        items.add("DMSc, DMedSc");
        items.add("DS, DSurg");*/



       spinnerDialog = new SpinnerDialog(PatientRegister.this,items,"");
       spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
           @Override
           public void onClick(String s, int i) {
               Toast.makeText(PatientRegister.this, "Doctor Selected: "+s, Toast.LENGTH_SHORT).show();
               selected_doctor = s;

           }
       });

       btnChooseDoctor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               for(int i=0;i<15;i++)
               items.add("Doctor "+i);
               spinnerDialog.showSpinerDialog();
           }
       });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientRegister.this, DoctorLogin.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(PatientRegister.this, "button pressed", Toast.LENGTH_SHORT).show();

                first_name              = editTextFirstName.getText().toString();
                last_name               = editTextLastName.getText().toString();
                phone_number            = editTextPhone.getText().toString();
                email_id                = editTextEmail.getText().toString();
                password                = editTextPassword.getText().toString();
                uid                     = editTextUID.getText().toString();
                full_address            = editTextAddress.getText().toString();
                selectedRadioButtonID   = radioSexGroup.getCheckedRadioButtonId();
                dob                     = editTextDOB.getText().toString();

                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                String selectedRadioButtonText = selectedRadioButton.getText().toString();


                if (TextUtils.isEmpty(first_name)) {
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(last_name)) {
                    Toast.makeText(getApplicationContext(), "Enter last name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone_number)) {
                    Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email_id)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedRadioButtonID != -1) {
                    //Toast.makeText(PatientRegister.this, selectedRadioButtonText, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PatientRegister.this, "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();
                }


                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth.createUserWithEmailAndPassword(email_id, password)
                        .addOnCompleteListener(PatientRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(PatientRegister.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(PatientRegister.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //startActivity(new Intent(DoctorRegister.this, DoctorMain.class));
                                    //finish();
                                    Toast.makeText(PatientRegister.this, "SIGNED IN DONE", Toast.LENGTH_SHORT).show();


                                    //GETTING DATA FROM RADIO BUTTON
                                    selectedRadioButtonID = radioSexGroup.getCheckedRadioButtonId();
                                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                                    String selectedRadioButtonText = selectedRadioButton.getText().toString();


                                    Map<String, Object> note = new HashMap<>();
                                    note.put(KEY_FIRSTNAME, first_name);
                                    note.put(KEY_LASTNAME, last_name);
                                    note.put(KEY_PHONE, phone_number);
                                    note.put(KEY_EMAIL, email_id);
                                    note.put(KEY_UID, uid);
                                    note.put(KEY_PASSWORD, password);
                                    note.put(KEY_DOCTOR,selected_doctor);
                                    note.put(KEY_DOB,dob);
                                    note.put(KEY_ADDRESS, full_address);
                                    note.put(KEY_GENDER, selectedRadioButtonText);
                                    progressBar.setVisibility(View.VISIBLE);
                                    db.collection("Patient").document(auth.getUid()).set(note)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(PatientRegister.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(PatientRegister.this, PatientMain.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(PatientRegister.this, "Error!", Toast.LENGTH_SHORT).show();
                                                    Log.d(TAG, e.toString());
                                                }
                                            });
                                }
                            }
                        });
            }
        });
    }
}
