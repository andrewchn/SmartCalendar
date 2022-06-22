package com.example.smartcalendar;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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

    public void getEventsFromCloud() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documentList = task.getResult().getDocuments();
                    for (int i = 0; i < documentList.size(); i++) {
                        Map<String, Object> docData = documentList.get(i).getData();
                        long creationMillis = Long.parseLong(documentList.get(i).getId());
                        String title = (String) docData.get("title");
                        long startMillis = (long) docData.get("startTime");
                        long endMillis = (long) docData.get("endTime");
                        Calendar startTime = Calendar.getInstance();
                        Calendar endTime = Calendar.getInstance();
                        startTime.setTimeInMillis(startMillis);
                        endTime.setTimeInMillis(endMillis);
                        Event event = new Event(title, startTime, endTime, creationMillis);
                        String location = (String) docData.get("location");
                        if (location != null && !location.isEmpty())
                            event.setLocation(location);
                        if (docData.get("alert1") != null) {
                            Calendar alert1 = Calendar.getInstance();
                            alert1.setTimeInMillis((long) docData.get("alert1"));
                            event.setAlert1(alert1);
                        }
                        if (docData.get("alert2") != null) {
                            Calendar alert2 = Calendar.getInstance();
                            alert2.setTimeInMillis((long) docData.get("alert2"));
                            event.setAlert1(alert2);
                        }
                        eventList.add(event);
                    }
                }
            }
        });
    }
}
