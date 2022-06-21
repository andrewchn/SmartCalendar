package com.example.smartcalendar;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class EventList {
    ArrayList<Event> eventList;
    public EventList() {
        this.eventList = new ArrayList<>();
    }

    public void add(Event newEvent) {
        eventList.add(newEvent);
    }

    public ArrayList<Event> getAllEventsAtDay(Calendar day) {
        Log.d("debug", "getAllEventsAtDay: day: " + day.get(Calendar.DAY_OF_MONTH));
        ArrayList<Event> eventsOnDay = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i ++) {
            Event currentEvent = eventList.get(i);
            long eventStartMillis = currentEvent.getStartDate().getTimeInMillis();
            long eventEndMillis = currentEvent.getEndDate().getTimeInMillis();
            long dayStartMillis = day.getTimeInMillis();
            long dayEndMillis = dayStartMillis + 24 * 60 * 60 * 1000;
            if ((eventStartMillis > dayStartMillis && eventStartMillis < dayEndMillis)
                    || (eventEndMillis > dayStartMillis && eventEndMillis < dayEndMillis)
                    || (eventEndMillis > dayEndMillis && eventStartMillis < dayStartMillis)) {
                // add sorting later
                eventsOnDay.add(currentEvent);
                Log.d("debug", "getAllEventsAtDay: event day: " + currentEvent.getStartDate().get(Calendar.DAY_OF_MONTH) + " " + currentEvent.getEndDate().get(Calendar.DAY_OF_MONTH));
            }
        }
        return eventsOnDay;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public int size() {
        return eventList.size();
    }
}
