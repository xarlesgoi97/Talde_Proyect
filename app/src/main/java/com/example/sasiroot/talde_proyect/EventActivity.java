package com.example.sasiroot.talde_proyect;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class EventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected ListView eventlist;
    protected ArrayList<Event> events;
    protected ArrayAdapter eventAdapter;
    protected Button star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        this.setTitle(R.string.app_name);
        this.star = this.findViewById(R.id.star);



        this.events = new ArrayList<>();
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.mipmap.ic_leke));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));
        this.events.add(new Event("Lekeitioko Jaiak", "Lekeitio", "19/09/07", R.drawable.ic_add_black_24dp));









        this.eventAdapter = new EventsAdapter(this,events);

        this.eventlist = this.findViewById(R.id.eventList);
        this.eventlist.setAdapter(eventAdapter);


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
                    info();
                }
            }


        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

        } else if (id == R.id.nav_manage) {

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
    private void info() {
        Intent i = new Intent(this, LoginActivity.class);
        this.startActivity(i);
    }



}
