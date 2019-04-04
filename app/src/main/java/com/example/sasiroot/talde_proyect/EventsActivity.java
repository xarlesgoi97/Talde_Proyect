package com.example.sasiroot.talde_proyect;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventsActivity extends AppCompatActivity {

    private Button logout;
    private TextView userName;
    private ImageView userPhoto;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        this.setTitle(R.string.app_name);

        this.logout = findViewById(R.id.logout);
        this.userName = findViewById(R.id.user_name);
        this.userPhoto = findViewById(R.id.user_photo);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                prueba();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String userNameFirebase = intent.getStringExtra("user_name");
        Uri userPhotoFirebase = Uri.parse(extras.getString("user_photo"));

        userName.setText(userNameFirebase);

        userPhoto.setImageURI(userPhotoFirebase);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    private void prueba() {
        Intent i = new Intent(this,prueba.class);

        startActivity(i);
    }

    private void writeNewUser(String eventID, String name, String lugar) {
        Event event = new Event(name, lugar);

        mDatabase.child("events").child(eventID).setValue(event);
    }

}
