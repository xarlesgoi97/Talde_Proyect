package com.example.sasiroot.talde_proyect;

import android.graphics.Bitmap;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable{
    private Bitmap bitmap;
    private String nombre;
    private String lugar;
    private String fecha;
    private String descripcion;
    private int imagen;

    public Event(String nombre, String lugar, String fecha, int imagen, String descripcion) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Event{" +
                ", Nombre='" + getNombre() + '\'' +
                ", Lugar='" + getLugar() + '\'' +
                ", Fecha='" + getFecha() + '\'' +
                ", Descripcion='" + getDescripcion() + '\'' +
                '}';
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}