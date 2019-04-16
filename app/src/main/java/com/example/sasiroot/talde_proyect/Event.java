package com.example.sasiroot.talde_proyect;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    public Long idEvent;
    public String title;
    public String city;
    public String eventDay;
    public String where;
    public String description;
    public String eventStart;
    public String eventEnd;
    public Uri photo;
    public Date createDate;

    public Event() {

    }

    public Event(/*Long idEvent,*/ String title, String city, String eventDay, String where, String eventStart, String eventEnd, String description/*, Uri photo*/, Date createDate) {
//        this.idEvent = idEvent;
        this.title = title;
        this.city = city;
        this.eventDay = eventDay;
        this.where = where;
        this.description = description;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;

//        this.photo = photo;
        this.createDate = createDate;
    }



    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {


        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }


    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", eventDay='" + eventDay + '\'' +
                ", where='" + where + '\'' +
                ", description='" + description + '\'' +
                ", eventStart='" + eventStart + '\'' +
                ", eventEnd='" + eventEnd + '\'' +
                ", photo=" + photo +
                ", createDate=" + createDate +
                '}';
    }
}
