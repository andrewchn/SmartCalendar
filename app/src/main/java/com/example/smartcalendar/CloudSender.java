package com.example.smartcalendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Calendar;

public class CloudSender implements CloudGetter.IFromCloudGetter {
    private FirebaseFirestore database;
    private IFromCloudSender sendData;
    private CloudGetter cloudGetter;
    private Event event;

    public CloudSender(Fragment parent) {
        this.database = FirebaseFirestore.getInstance();
        this.cloudGetter = new CloudGetter(this);
        if (parent instanceof IFromCloudSender) {
            sendData = (IFromCloudSender) parent;
        } else {
            throw new RuntimeException(parent + " must implement IFromCloudSender");
        }
    }

    public void write(Event event) {
        this.event = event;
        cloudGetter.getAllEvents();
    }

    @Override
    public void finishedGetEventList(ArrayList<Event> eventList) {
        boolean inConflict;
        ArrayList<Event> eventsToMove = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i ++) {
            Event currentEvent = eventList.get(i);
            long eventStart = event.getStartDate().getTimeInMillis();
            long eventEnd = event.getEndDate().getTimeInMillis();
            long currentStart = currentEvent.getStartDate().getTimeInMillis();
            long currentEnd = currentEvent.getEndDate().getTimeInMillis();
            inConflict = !(eventStart >= currentEnd || eventEnd <= currentStart);
            if (inConflict) {
                if (event.isSmartEvent()) {
                    long duration = eventEnd - eventStart;
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    start.setTimeInMillis(currentEnd);
                    end.setTimeInMillis(currentEnd + duration);
                    event.setStartDate(start);
                    event.setEndDate(end);
                } else if (currentEvent.isSmartEvent()){
                    eventsToMove.add(currentEvent);
                }
            }
        }

        for (Event currentEvent : eventsToMove) {
            eventList.remove(currentEvent);
        }
        eventList.add(event);

        String eventId = "" + event.getCreationMillis();
        DocumentReference eventDoc = database.collection("events").document(eventId);
        eventDoc.set(event.getEventMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sendData.finishedWrite(task.isSuccessful(), eventsToMove, eventList);
            }
        });
    }

    public void shuffleSmartEvents(ArrayList<Event> smartEventsToWrite, ArrayList<Event> allEvents) {
        if (smartEventsToWrite.isEmpty()) {
            sendData.finishedWrite(true, smartEventsToWrite, allEvents);
        } else {
            this.event = smartEventsToWrite.get(0);
            smartEventsToWrite.remove(0);
            boolean inConflict;
            for (int i = 0; i < allEvents.size(); i++) {
                Event currentEvent = allEvents.get(i);
                long eventStart = event.getStartDate().getTimeInMillis();
                long eventEnd = event.getEndDate().getTimeInMillis();
                long currentStart = currentEvent.getStartDate().getTimeInMillis();
                long currentEnd = currentEvent.getEndDate().getTimeInMillis();
                inConflict = !(eventStart >= currentEnd || eventEnd <= currentStart);
                if (inConflict) {
                    long duration = eventEnd - eventStart;
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    start.setTimeInMillis(currentEnd);
                    end.setTimeInMillis(currentEnd + duration);
                    event.setStartDate(start);
                    event.setEndDate(end);
                }
            }

            String eventId = "" + event.getCreationMillis();
            DocumentReference eventDoc = database.collection("events").document(eventId);
            eventDoc.set(event.getEventMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    shuffleSmartEvents(smartEventsToWrite, allEvents);
                }
            });
        }
    }

    public interface IFromCloudSender {
        void finishedWrite(boolean successful, ArrayList<Event> eventsToMove, ArrayList<Event> allEvents);
    }
}
