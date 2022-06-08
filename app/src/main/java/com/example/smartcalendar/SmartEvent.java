package com.example.smartcalendar;

import android.location.Location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SmartEvent extends Event {
    int weeklyFrequency;
    int monthlyFrequency;
    int yearlyFrequency;
    Calendar dueDate;

    public SmartEvent(String title, Calendar startDate, Calendar endDate, int weeklyFrequency, int monthlyFrequency, int yearlyFrequency, Calendar dueDate) {
        super(title, startDate, endDate);
        this.weeklyFrequency = weeklyFrequency;
        this.monthlyFrequency = monthlyFrequency;
        this.yearlyFrequency = yearlyFrequency;
        this.dueDate = dueDate;
    }
}
