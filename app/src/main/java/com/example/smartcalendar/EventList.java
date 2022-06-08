package com.example.smartcalendar;

import java.time.LocalDate;
import java.time.MonthDay;
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
            }
        }
        return eventsOnDay;
    }
}
