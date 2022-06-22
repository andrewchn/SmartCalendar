package com.example.smartcalendar.ViewCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.smartcalendar.EditEventFragment;
import com.example.smartcalendar.Event;
import com.example.smartcalendar.R;
import com.google.firebase.FirebaseApp;

import java.time.Year;
import java.util.Calendar;

public class ViewCalendarActivity extends AppCompatActivity implements ViewCalendarMonthFragment.IFromViewCalendarMonth,
        ViewCalendarDayFragment.IFromViewCalendarDay, EditEventFragment.IFromEditEvent {

    private ViewCalendarMonthFragment viewMonths;
    private ViewCalendarDayFragment viewDays;
    private EditEventFragment editEvent;
    private Button buttonSettings;
    private Button buttonViewSmartEvents;
    private FragmentContainerView calendarFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonViewSmartEvents = findViewById(R.id.buttonViewSmartEvents);
        calendarFragmentContainer = findViewById(R.id.fragmentContainerCalendar);

        viewMonths = new ViewCalendarMonthFragment(Year.now());
        editEvent = new EditEventFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerCalendar, viewMonths)
                .addToBackStack("months")
                .commit();
    }

    @Override
    public void selectedDay(Calendar date) {
        viewDays = new ViewCalendarDayFragment(date);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerCalendar, viewDays)
                .addToBackStack("day")
                .commit();
    }

    @Override
    public void selectedEvent(Event event) {
        editEvent.setEventToEdit(event);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerMain, editEvent)
                .addToBackStack("edit event")
                .commit();
    }

    @Override
    public void selectedAddEvent(Calendar day) {
        editEvent.createNewEvent(day);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerMain, editEvent)
                .addToBackStack("edit event")
                .commit();
    }

    @Override
    public void saveEvent() {
        getSupportFragmentManager().popBackStackImmediate();

    }
}