package com.example.smartcalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Event {
    private String title;
    private String location;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar alert1;
    private Calendar alert2;

    public long getCreationMillis() {
        return creationMillis;
    }

    private final long creationMillis;

    // base constructor
    public Event(String title, Calendar startDate, Calendar endDate) {
        this.creationMillis = System.currentTimeMillis();
        this.title = title;
        if (startDate.after(endDate)) {
            this.startDate = endDate;
            this.endDate = startDate;
        } else {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    public Event(String title, Calendar startDate, Calendar endDate, long creationMillis) {
        this.creationMillis = creationMillis;
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

    public String getLocation() {
        if (location == null)
            throw new IllegalStateException("no location exists");
        return location;
    }

    public void setTitle(String title) {
        if (title == null)
            throw new IllegalArgumentException("title cannot be null");
        this.title = title;
    }

    public void setLocation(String location) {
        if (location == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        this.location = location;
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

    public String getStringDuration() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");
        return sdf.format(startDate.getTime()) + " to " + sdf.format(endDate.getTime());
    }

    public Calendar getAlert1() {
        return alert1;
    }

    public void setAlert1(Calendar alert1) {
        this.alert1 = alert1;
    }

    public Calendar getAlert2() {
        return alert2;
    }

    public void setAlert2(Calendar alert2) {
        this.alert2 = alert2;
    }

    public int getNumAlerts() {
        int numAlerts = 0;
        if (alert1 != null)
            numAlerts ++;
        if (alert2 != null)
            numAlerts ++;
        return numAlerts;
    }

    public Map<String, Object> getEventMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("startTime", startDate.getTimeInMillis());
        data.put("endTime", startDate.getTimeInMillis());
        if (hasLocation())
            data.put("location", location);
        if (alert1 != null)
            data.put("alert1", alert1.getTimeInMillis());
        if (alert2 != null)
            data.put("alert2", alert2.getTimeInMillis());
        return data;
    }
}
