package com.example.docpat.Doctor;

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

import com.example.docpat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoctorRegister extends AppCompatActivity {


    private EditText editTextFirstName, editTextLastName, editTextPhone, editTextEmail, editTextAddress, editTextPassword, editTextDOB, editTextRegno;
    ImageButton btnReleaseDatePicker;
    private int mYear, mMonth, mDay;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    int selectedRadioButtonID;
    private Spinner spinner1;
    public static final String TAG = "MainActivity";
    String first_name, last_name, phone_number, email_id, password, full_address, degree, dob, regno;

    public static final String KEY_FIRSTNAME   = "first_name";
    public static final String KEY_LASTNAME    = "last_name";
    public static final String KEY_PHONE       = "phone_number";
    public static final String KEY_DOB         = "dob";
    public static final String KEY_EMAIL       = "email_id";
    public static final String KEY_PASSWORD    = "password";
    public static final String KEY_GENDER      = "gender";
    public static final String KEY_REGNO      = "regno";
    public static final String KEY_DEGREE      = "doctor_degree";
    public static final String KEY_ADDRESS     = "full_address";

    public static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("REGISTER");
       /* if (auth.getCurrentUser() != null) {
            startActivity(new Intent(DoctorRegister.this, DoctorMain.class));
            finish();
        }*/

        editTextFirstName       = (EditText) findViewById(R.id.first_name);
        editTextLastName        = (EditText) findViewById(R.id.last_name);
        editTextPhone           = (EditText) findViewById(R.id.phone_number);
        editTextEmail           = (EditText) findViewById(R.id.email);
        editTextAddress         = (EditText) findViewById(R.id.address);
        editTextDOB             = (EditText) findViewById(R.id.DOB);
        editTextRegno           = (EditText) findViewById(R.id.doctor_reg_no);
        btnSignIn               = (Button) findViewById(R.id.sign_in_button);
        btnSignUp               = (Button) findViewById(R.id.sign_up_button);
        editTextPassword        = (EditText) findViewById(R.id.password);
        progressBar             = (ProgressBar) findViewById(R.id.progressBar);
        btnReleaseDatePicker    = (ImageButton) findViewById(R.id.btn_release_date);

        radioSexGroup           = (RadioGroup) findViewById(R.id.radioSex);


        btnReleaseDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(DoctorRegister.this,
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

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems(
                "MD(Res), DM",
                "PhD, DPhi",
                "MCM",
                "MMSc, MMedSc",
                "Marshmallow",
                "MM, MMed",
                "MPhil",
                "MS, MSurg, MChir, MCh, ChM, CM",
                "DCM",
                "MSc",
                "DClinSurg",
                "DMSc, DMedSc",
                "DS, DSurg"
        );
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                degree = item;
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorRegister.this, DoctorLogin.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DoctorRegister.this, "button pressed", Toast.LENGTH_SHORT).show();

                first_name              = editTextFirstName.getText().toString();
                last_name               = editTextLastName.getText().toString();
                phone_number            = editTextPhone.getText().toString();
                email_id                = editTextEmail.getText().toString();
                password                = editTextPassword.getText().toString();
                full_address            = editTextEmail.getText().toString();
                selectedRadioButtonID   = radioSexGroup.getCheckedRadioButtonId();
                dob                     = editTextDOB.getText().toString();
                regno                   = editTextRegno.getText().toString();

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
                if (selectedRadioButtonID != -1)
                    Toast.makeText(DoctorRegister.this, selectedRadioButtonText, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DoctorRegister.this, "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();



                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth.createUserWithEmailAndPassword(email_id, password)
                        .addOnCompleteListener(DoctorRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(DoctorRegister.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(DoctorRegister.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //startActivity(new Intent(DoctorRegister.this, DoctorMain.class));
                                    //finish();
                                    Toast.makeText(DoctorRegister.this, "SIGNED IN DONE", Toast.LENGTH_SHORT).show();


                                    //GETTING DATA FROM RADIO BUTTON
                                    selectedRadioButtonID = radioSexGroup.getCheckedRadioButtonId();
                                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                                    String selectedRadioButtonText = selectedRadioButton.getText().toString();


                                    Map<String, Object> note = new HashMap<>();
                                    note.put(KEY_FIRSTNAME, first_name);
                                    note.put(KEY_LASTNAME, last_name);
                                    note.put(KEY_PHONE, phone_number);
                                    note.put(KEY_EMAIL, email_id);
                                    note.put(KEY_PASSWORD, password);
                                    note.put(KEY_DEGREE,degree);
                                    note.put(KEY_DOB,dob);
                                    note.put(KEY_REGNO,regno);
                                    note.put(KEY_ADDRESS, full_address);
                                    note.put(KEY_GENDER, selectedRadioButtonText);

                                    db.collection("Doctor").document(auth.getUid()).set(note)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(DoctorRegister.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(DoctorRegister.this, DoctorMain.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(DoctorRegister.this, "Error!", Toast.LENGTH_SHORT).show();
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
