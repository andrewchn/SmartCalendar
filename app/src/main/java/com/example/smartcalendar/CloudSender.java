package com.example.smartcalendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CloudSender {
    private FirebaseFirestore database;
    private IFromCloudSender sendData;

    public CloudSender(Fragment parent) {
        this.database = FirebaseFirestore.getInstance();
        if (parent instanceof IFromCloudSender) {
            sendData = (IFromCloudSender) parent;
        } else {
            throw new RuntimeException(parent + " must implement IFromCloudSender");
        }
    }

    public void write(Event event) {
        String eventId = "" + event.getCreationMillis();
        DocumentReference eventDoc = database.collection("events").document(eventId);
        eventDoc.set(event.getEventMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sendData.finishedWrite(task.isSuccessful());
            }
        });
    }

    public interface IFromCloudSender {
        void finishedWrite(boolean successful);
    }
}
