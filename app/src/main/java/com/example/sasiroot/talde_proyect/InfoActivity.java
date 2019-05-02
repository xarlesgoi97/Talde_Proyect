package com.example.sasiroot.talde_proyect;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {


    private ImageView background;
    private TextView titulo;
    private TextView lugar;
    private TextView fecha;
    private TextView descripcion;
    private TextView ciudad;
    private TextView start;
    private TextView end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        background = findViewById(R.id.background_image);
        titulo = findViewById(R.id.textTitulo);
        lugar = findViewById(R.id.textLugar);
        fecha = findViewById(R.id.textFecha);
        descripcion = findViewById(R.id.textDescripcion);
        ciudad = findViewById(R.id.textCiudad);
        start = findViewById(R.id.textEmpieza);
        end = findViewById(R.id.textTermina);

        Intent intent  = getIntent();
        Event eventItem = (Event) intent.getExtras().get("event");

        //background.setBackgroundResource(eventItem.getImagen());
        titulo.setText(eventItem.getTitle());
        lugar.setText(eventItem.getWhere());
        fecha.setText(eventItem.getEventDay());
        descripcion.setText(eventItem.getDescription());
        ciudad.setText(eventItem.getCity());
        start.setText(eventItem.getEventStart());
        end.setText(eventItem.getEventEnd());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Añadiedo a favoritos", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}