package com.example.sasiroot.talde_proyect;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class EventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected ListView eventlist;
    protected ArrayList<Event> events;
    protected ArrayAdapter eventAdapter;
    protected Button star;

    //TAG
    private static final String TAG = "EventActivity";

    //FIREBASE
    private FirebaseFirestore db;
    protected DocumentReference docRef;
    protected CollectionReference eventsData;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        this.setTitle(R.string.app_name);
        this.star = this.findViewById(R.id.star);
        getAllDocs();
        Point screen;
        screen = new Point();
        Display display = this.getWindowManager().getDefaultDisplay();
        display.getSize(screen);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap b = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_lekeitio1, opts);



        int tWidth  = b.getWidth();
        int tHeight = b.getHeight();
        int sWidth  = screen.x;

        int p = (sWidth * 100) / tWidth;
        int sHeight = (b.getHeight() * p)/100;
        Bitmap bitmap = Bitmap.createScaledBitmap(b, screen.x, sHeight,false );

        this.events = new ArrayList<>();
        this.eventAdapter = new EventsAdapter(this,events);

        this.eventlist = this.findViewById(R.id.eventList);
        this.eventlist.setAdapter(eventAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });


        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(eventlist),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                events.remove(position);
                            }
                        });

        eventlist.setOnTouchListener(touchListener);
        eventlist.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        eventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(EventActivity.this, "Position " + position, LENGTH_SHORT).show();
                    info(position);
                }

            }


        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void addEvent() {
        Intent intent  = getIntent();
        user = (FirebaseUser) intent.getExtras().get("user");

        Intent i = new Intent(this,AddEventActivity.class);
        i.putExtra("user", user);
        //i.putExtra("user_photo", mAuth.getCurrentUser().getPhotoUrl().toString());

        startActivityForResult(i,RESULT_OK);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            logout();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void compartir(View view) {

        Intent i = new Intent(this, CreateaccActivity.class);
        this.startActivity(i);

    }

    public void star(View view) {

        if(view.isActivated()){
            view.setActivated(false);
        }else{
            view.setActivated(true);
            Toast.makeText(this, "Añadiendo a favoritos", Snackbar.LENGTH_LONG).show();

        }

    }
    private void info(Integer position) {
        Intent i = new Intent(this, InfoActivity.class);
        Event event = (Event) eventlist.getItemAtPosition(position);
        i.putExtra("event", event);

        this.startActivityForResult(i,RESULT_OK);
    }

    private void logout() {
        Intent i = new Intent(this,Prueba.class);

        startActivity(i);
    }
    public void getAllDocs() {
        // [START get_multiple_all]
        db = FirebaseFirestore.getInstance();
        db.collection("events").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){

                                Event e = d.toObject(Event.class);
                                events.add(e);


                            }
                            eventAdapter.notifyDataSetChanged();

                        }


                    }
                });
        // [END get_multiple_all]
    }



}