package com.example.sasiroot.talde_proyect;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EventsActivity extends AppCompatActivity {

    //BUTTONS, IMAGEVIEW, TEXTVIEW
    private Button btnLogout;
    private Button btnSend;
    //EVENT DATA
    private EditText txtTitle;
    private EditText txtCity;
    private EditText txtWhere;
    private EditText txtEventStart;
    private EditText txtEventEnd;
    private EditText txtDescription;
    private TextView txtUserName;

    private ImageView imgUserPhoto;


    //FIREBASE DB
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        this.setTitle(R.string.app_name);

        this.btnLogout = findViewById(R.id.logout);
        this.txtUserName = findViewById(R.id.user_name);
        this.imgUserPhoto = findViewById(R.id.user_photo);
        this.btnSend = findViewById(R.id.btnSend);
        this.txtTitle = findViewById(R.id.txtTitle);
        this.txtCity = findViewById(R.id.txtCity);
        this.txtWhere = findViewById(R.id.txtWhere);
        this.txtEventStart = findViewById(R.id.txtEventStart);
        this.txtEventEnd = findViewById(R.id.txtEventEnd);
        this.txtDescription = findViewById(R.id.txtDescription);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                prueba();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeEvent(txtTitle.getText().toString(),txtCity.getText().toString(),txtWhere.getText().toString(),txtEventStart.getText().toString(),txtEventEnd.getText().toString(),txtDescription.getText().toString(), new Date());
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String userNameFirebase = intent.getStringExtra("user_name");
        Uri userPhotoFirebase = Uri.parse(extras.getString("user_photo"));

        txtUserName.setText(userNameFirebase);

        imgUserPhoto.setImageURI(userPhotoFirebase);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    private void prueba() {
        Intent i = new Intent(this,Prueba.class);

        startActivity(i);
    }


    private void writeEvent(/*Long idEvent,*/String title, String city, String where,  String eventStart, String eventEnd, String description/*, Uri photo*/, Date createDate) {
        Event event = new Event(/*idEvent,*/title, city, where, eventStart, eventEnd, description/*, photo*/, createDate);
        /*event.setTitle(title);
        event.setCity(city);
        event.setWhere(where);
        event.setDescription(description);
        event.setEventStart(eventStart);
        event.setEventEnd(eventEnd);
        event.setCreateDate(new Date());*/

        mDatabase.child("events").child("event").setValue(event);
    }

}
