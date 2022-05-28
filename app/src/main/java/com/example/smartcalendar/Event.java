package com.example.smartcalendar;

import android.location.Location;
import java.util.ArrayList;
import java.util.Date;

public abstract class Event {
    private String title;
    private ArrayList<Integer> alerts;
    private int duration;
    private Location location;
    private String calendar;
    private Date date;

    // Full constructor
    public Event(String title, ArrayList<Integer> alerts, int duration, Location location, String calendar, Date date) {
        this.title = title;
        this.alerts = alerts;
        this.duration = duration;
        this.location = location;
        this.calendar = calendar;
        this.date = date;
    }

    // Constructor without alerts or location
    public Event(String title, int duration, String calendar, Date date) {
        this.title = title;
        this.duration = duration;
        this.calendar = calendar;
        this.date = date;
    }

    // Constructor without location
    public Event(String title, ArrayList<Integer> alerts, int duration, String calendar, Date date) {
        this.title = title;
        this.alerts = alerts;
        this.duration = duration;
        this.calendar = calendar;
        this.date = date;
    }

    // Constructor without alerts
    public Event(String title, int duration, Location location, String calendar, Date date) {
        this.title = title;
        this.duration = duration;
        this.location = location;
        this.calendar = calendar;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Integer> getAlerts() {
        if (alerts == null || alerts.isEmpty())
            throw new IllegalStateException("no alerts exist");
        return alerts;
    }

    public int getDuration() {
        return duration;
    }

    public Location getLocation() {
        if (location == null)
            throw new IllegalStateException("no location exists");
        return location;
    }

    public void setTitle(String title) {
        if (title == null)
            throw new IllegalArgumentException("title cannot be null");
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        this.location = location;
    }

    public void addAlert(int newAlert) {
        this.alerts.add(newAlert);
    }

    public void removeAlert(int index) {
        if (index < 0 || index > alerts.size() - 1) {
            throw new IllegalArgumentException("invalid alerts index");
        }
        this.alerts.remove(index);
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        if (calendar == null)
            throw new IllegalArgumentException("calendar cannot be null");
        this.calendar = calendar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null)
            throw new IllegalArgumentException("date cannot be null");
        this.date = date;
    }
}
