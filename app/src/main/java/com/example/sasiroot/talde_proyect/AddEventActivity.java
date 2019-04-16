package com.example.sasiroot.talde_proyect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "AddEventActivity";

    //BUTTONS, IMAGEVIEW, TEXTVIEW
    private Button btnLogout;
    private Button btnSend;
    //EVENT DATA
    private EditText txtTitle;
    private EditText txtCity;
    private EditText txtEventDay;
    private EditText txtWhere;
    private EditText txtEventStart;
    private EditText txtEventEnd;
    private EditText txtDescription;
    private TextView txtUserName;

    private ImageView imgUserPhoto;


    //FIREBASE DATANASE CLOUDFIRE
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        this.setTitle(R.string.app_name);

        this.btnLogout = findViewById(R.id.logout);
        this.txtUserName = findViewById(R.id.user_name);
        this.imgUserPhoto = findViewById(R.id.user_photo);
        this.btnSend = findViewById(R.id.btnSend);
        this.txtTitle = findViewById(R.id.txtTitle);
        this.txtCity = findViewById(R.id.txtCity);
        this.txtEventDay = findViewById(R.id.txtEventDay);
        this.txtWhere = findViewById(R.id.txtWhere);
        this.txtEventStart = findViewById(R.id.txtEventStart);
        this.txtEventEnd = findViewById(R.id.txtEventEnd);
        this.txtDescription = findViewById(R.id.txtDescription);
        //CLOUDFIRE DATABASE
        db = FirebaseFirestore.getInstance();



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
                writeEvent(txtTitle.getText().toString(),txtCity.getText().toString(),txtEventDay.getText().toString(),txtWhere.getText().toString(),txtEventStart.getText().toString(),txtEventEnd.getText().toString(),txtDescription.getText().toString(), new Date());
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

//        String userNameFirebase = intent.getStringExtra("user_name");
//        Uri userPhotoFirebase = Uri.parse(extras.getString("user_photo"));

//        txtUserName.setText(userNameFirebase);

//        imgUserPhoto.setImageURI(userPhotoFirebase);
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


    private void writeEvent(String title, String city, String eventDay, String where,  String eventStart, String eventEnd, String description/*, Uri photo*/, Date createDate) {
        Event event = new Event(title, city, eventDay, where, eventStart, eventEnd, description/*, photo*/, createDate);
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("title", event.getTitle());
        eventData.put("city", event.getCity());
        eventData.put("eventDay", event.getEventDay());
        eventData.put("where", event.getWhere());
        eventData.put("eventStart", event.getEventStart());
        eventData.put("eventEnd", event.getEventEnd());
        eventData.put("description", event.getDescription());
        eventData.put("createDate", event.getCreateDate());

        db.collection("events")
                .add(eventData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

}
