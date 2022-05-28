package com.example.smartcalendar;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class SmartEvent extends Event {
    int weeklyFrequency;
    int monthlyFrequency;
    int yearlyFrequency;
    Date dueDate;


    public SmartEvent(String title, ArrayList<Integer> alerts, int duration, Location location, String calendar, Date date) {
        super(title, alerts, duration, location, calendar, date);
    }

    public SmartEvent(String title, int duration, String calendar, Date date) {
        super(title, duration, calendar, date);
    }

    public SmartEvent(String title, ArrayList<Integer> alerts, int duration, String calendar, Date date) {
        super(title, alerts, duration, calendar, date);
    }

    public SmartEvent(String title, int duration, Location location, String calendar, Date date) {
        super(title, duration, location, calendar, date);
    }
}
