package com.example.docpat.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.docpat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.docpat.Doctor.DoctorRegister.KEY_FIRSTNAME;
import static com.example.docpat.Doctor.DoctorRegister.KEY_LASTNAME;

public class DoctorMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private DocumentReference noteRef;

    TextView tv_email, welcome_text, nav_head_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        View header=navigationView.getHeaderView(0);

        tv_email = (TextView) header.findViewById(R.id.textView_emailid);
        welcome_text = (TextView)findViewById(R.id.welcome_text);
        nav_head_name = (TextView)header.findViewById(R.id.nav_head_name);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            tv_email.setText(email);
        }



        noteRef = db.collection("Doctor").document(auth.getUid());
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        if(documentSnapshot.exists()){
                            String first_name = documentSnapshot.getString(KEY_FIRSTNAME);
                            String last_name = documentSnapshot.getString(KEY_LASTNAME);
                           /* String phone_number = documentSnapshot.getString(KEY_PHONE);
                            String gender = documentSnapshot.getString(KEY_GENDER);
                            String dob = documentSnapshot.getString(KEY_DOB);
                            String email = documentSnapshot.getString(KEY_EMAIL);
                            String address = documentSnapshot.getString(KEY_ADDRESS);
                            String degree = documentSnapshot.getString(KEY_DEGREE);
                            String regno = documentSnapshot.getString(KEY_REGNO);*/

                            welcome_text.setText("Welcome, "+first_name +" "+ last_name);
                            nav_head_name.setText(first_name +" "+ last_name);
                            /*textViewLastName.setText(last_name);
                            textViewPhone.setText(phone_number);
                            textViewGender.setText(gender);
                            textViewEmail.setText(email);
                            textViewAddress.setText(address);
                            textViewDegree.setText(degree);
                            textViewDOB.setText(dob);
                            textViewRegNo.setText(regno);*/

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            startActivity(new Intent(DoctorMain.this, DoctorProfile.class));

        } else if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(DoctorMain.this, DoctorLogin.class));
        } /*else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
