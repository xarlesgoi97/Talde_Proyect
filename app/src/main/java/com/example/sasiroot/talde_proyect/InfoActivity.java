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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        background = findViewById(R.id.background_image);
        titulo = findViewById(R.id.textTitulo);
        lugar = findViewById(R.id.textLugar);
        fecha = findViewById(R.id.textFecha);
        descripcion = findViewById(R.id.textDescripcion);
        ciudad = findViewById(R.id.textCiudad);

        Intent intent  = getIntent();
        Event eventItem = (Event) intent.getExtras().get("event");

        background.setBackgroundResource(eventItem.getImagen());
        titulo.setText(eventItem.getNombre());
        lugar.setText(eventItem.getLugar());
        fecha.setText(eventItem.getFecha());
        descripcion.setText(eventItem.getDescripcion());
        ciudad.setText(eventItem.getLugar());


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
