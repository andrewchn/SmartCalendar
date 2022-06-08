package com.example.smartcalendar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewCalendarActivity extends AppCompatActivity implements ViewCalendarMonthFragment.IFromViewCalendarMonth, ViewCalendarDayFragment.IFromViewCalendarDay {

    ViewCalendarMonthFragment viewMonths;
    ViewCalendarDayFragment viewDays;
    EventList allEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMonths = new ViewCalendarMonthFragment(Year.now());
        allEvents = new EventList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerCalendar, viewMonths)
                .addToBackStack("months")
                .commit();
    }

    @Override
    public void selectedDay(Calendar date) {
        getSupportFragmentManager().popBackStackImmediate();
        viewDays = new ViewCalendarDayFragment(allEvents, date);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerCalendar, viewDays)
                .addToBackStack("day")
                .commit();
    }

    @Override
    public void selectedEvent(Event event) {

    }

    @Override
    public void selectedAddEvent(Calendar day) {

    }
}