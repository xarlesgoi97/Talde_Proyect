package com.example.sasiroot.talde_proyect;

import java.util.Date;

public class Event {
    private String nombre;
    private String lugar;
    private String fecha;
    private int imagen;

    public Event(String nombre, String lugar, String fecha, int imagen) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.imagen = imagen;
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

    @Override
    public String toString() {
        return "Event{" +
                ", Nombre='" + getNombre() + '\'' +
                ", Lugar='" + getLugar() + '\'' +
                ", Fecha='" + getFecha() + '\'' +
                '}';
    }
}