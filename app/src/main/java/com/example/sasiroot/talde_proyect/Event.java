package com.example.sasiroot.talde_proyect;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    public String eventId, title, city, eventDay, where, description, eventStart, eventEnd, photoInfo, createBy;
    public Date createDate;

    public Event() {

    }

    public Event(String eventId, String title, String city, String eventDay, String where, String eventStart, String eventEnd, String description, String photoInfo, Date createDate, String createBy) {
        this.eventId = eventId;
        this.title = title;
        this.city = city;
        this.eventDay = eventDay;
        this.where = where;
        this.description = description;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.photoInfo = photoInfo;
        this.createDate = createDate;
        this.createBy = createBy;
    }



    public String geteventId() {
        return eventId;
    }

    public void seteventId(String eventId) {
        this.eventId = eventId;
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

    public String getPhotoInfo() {
        return photoInfo;
    }

    public void setPhotoInfo(String photoInfo) {
        this.photoInfo = photoInfo;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", eventDay='" + eventDay + '\'' +
                ", where='" + where + '\'' +
                ", description='" + description + '\'' +
                ", eventStart='" + eventStart + '\'' +
                ", eventEnd='" + eventEnd + '\'' +
                ", photoInfo=" + photoInfo +
                ", createDate=" + createDate +
                ", createBy=" + createBy +
                '}';
    }
}