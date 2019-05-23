package com.example.sasiroot.talde_proyect;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout swipeRefresh;
    protected ListView eventlist;
    protected ArrayList<Event> events;
    protected ArrayAdapter eventAdapter;
    protected Button star;
    private ProgressDialog pb;
    private NavigationView navigationView;
    private TextView userName, userEmail;


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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Point screen;
        screen = new Point();
        Display display = this.getWindowManager().getDefaultDisplay();
        display.getSize(screen);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap b = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_lekeitio1, opts);
        pb = new ProgressDialog(this);



        int tWidth  = b.getWidth();
        int tHeight = b.getHeight();
        int sWidth  = screen.x;

        int p = (sWidth * 100) / tWidth;
        int sHeight = (b.getHeight() * p)/100;
        Bitmap bitmap = Bitmap.createScaledBitmap(b, screen.x, sHeight,false );
        this.swipeRefresh = this.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDocs();
                swipeRefresh.setRefreshing(false);
            }
        });
        this.events = new ArrayList<>();
        this.eventAdapter = new EventsAdapter(this,events);

        this.eventlist = this.findViewById(R.id.eventList);
        this.eventlist.setAdapter(eventAdapter);

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        this.userName = header.findViewById(R.id.userName);
        this.userEmail = header.findViewById(R.id.userEmail);


        Intent intent = getIntent();
        user = (FirebaseUser) intent.getExtras().get("user");

        String userEmail = user.getEmail();
        String userName = user.getDisplayName();

        this.userName.setText(userName);
        this.userEmail.setText(userEmail);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });

        if (userEmail.compareTo("gotzongalletebeitia95@gmail.com")==0){
            fab.setVisibility(View.VISIBLE);

        }else{
            fab.setVisibility(View.INVISIBLE);
        }

        eventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                info(position);
            }


        });
        if (userEmail.compareTo("gotzongalletebeitia95@gmail.com")==0) {
            eventlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                    final Event item = (Event) eventlist.getItemAtPosition(position);

                    final CharSequence[] opciones = {"Aceptar", "Cancelar"};
                    final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(EventActivity.this);

                    alertOpciones.setTitle("Estas seguro de eliminar este evento?");
                    alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (opciones[i].equals("Aceptar")) {
                                pb.setTitle("Borrando evento...");
                                pb.show();
                                db.collection("events").document(item.geteventId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pb.dismiss();
                                                eventAdapter.remove(item);
                                                Toast.makeText(EventActivity.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                            } else {
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    alertOpciones.show();
                    return true;
                }

            });
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        this.navigationView.setNavigationItemSelectedListener(this);


    }




    @Override
    protected void onResume() {
        super.onResume();
        getAllDocs();

    }

    private void addEvent() {
        Intent intent  = getIntent();
        user = (FirebaseUser) intent.getExtras().get("user");

        Intent i = new Intent(this,AddEventActivity.class);
        i.putExtra("user", user);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
            back();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        this.startActivity(i);
    }

    private void logout() {
        Intent i = new Intent(this, LoginActivity.class);

        startActivity(i);
    }
    public void getAllDocs() {
        // [START get_multiple_all]
        eventAdapter.clear();
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
    }

    private void back() {

        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }



}