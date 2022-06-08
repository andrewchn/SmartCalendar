package com.example.smartcalendar;

import android.location.Location;

import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public abstract class Event {
    private String title;
    private ArrayList<Integer> alerts;
    private Location location;
    private Calendar startDate;
    private Calendar endDate;

    // base constructor
    public Event(String title, Calendar startDate, Calendar endDate) {
        this.title = title;
        if (startDate.after(endDate)) {
            this.startDate = endDate;
            this.endDate = startDate;
        } else {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Integer> getAlerts() {
        if (alerts == null || alerts.isEmpty())
            throw new IllegalStateException("no alerts exist");
        return alerts;
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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean hasLocation() {
        return location != null;
    }

    public boolean hasAlerts() {
        return !alerts.isEmpty();
    }

    public String getStringDuration() {
        return startDate.toString().substring(0, 16) + " to " + startDate.toString().substring(0, 16);
    }
}
