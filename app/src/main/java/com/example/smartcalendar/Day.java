package com.example.smartcalendar;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Day {
    private LocalDate date;
    private ArrayList<Event> events;

    public Day(LocalDate date) {
        this.date = date;
        this.events = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
