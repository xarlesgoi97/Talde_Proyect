package com.example.sasiroot.talde_proyect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(Context context, List<Event> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.events_list_item,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        TextView name = (TextView) convertView.findViewById(R.id.nombre);
        TextView title = (TextView) convertView.findViewById(R.id.lugar);
        TextView company = (TextView) convertView.findViewById(R.id.fecha);


        // Lead actual.
        Event event = getItem(position);

        // Setup.

        name.setText(event.getTitle());
        //title.setText(event.getLugar());
        //company.setText(event.getFecha());

        //avatar.setImageResource(event.getImagen());
        //avatar.setImageBitmap(event.getBitmap());

        return convertView;
    }


}
