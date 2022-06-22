package com.example.smartcalendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CloudGetter {
    private FirebaseFirestore database;
    private IFromCloudGetter sendData;

    public CloudGetter(Fragment parent) {
        this.database = FirebaseFirestore.getInstance();
        if (parent instanceof IFromCloudGetter) {
            sendData = (IFromCloudGetter) parent;
        } else {
            throw new RuntimeException(parent + " must implement IFromCloudGetter");
        }
    }

    public void getAllEventsAtDay(Calendar day) {
        ArrayList<Event> eventList = new ArrayList<>();
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
                        long dayStartMillis = day.getTimeInMillis();
                        long dayEndMillis = dayStartMillis + 24 * 60 * 60 * 1000;
                        if ((startMillis > dayStartMillis && startMillis < dayEndMillis)
                                || (endMillis > dayStartMillis && endMillis < dayEndMillis)
                                || (endMillis > dayEndMillis && endMillis < dayStartMillis)) {
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
                    sendData.finishedGetEventList(eventList);
                }
            }
        });
    }

    public interface IFromCloudGetter {
        void finishedGetEventList(ArrayList<Event> eventList);
    }
}
