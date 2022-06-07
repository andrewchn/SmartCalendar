package com.example.smartcalendar;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

public class ViewCalendarActivity extends AppCompatActivity implements ViewCalendarMonthFragment.IFromViewCalendarMonth {

    ViewCalendarMonthFragment viewMonths;
    ViewCalendarDayFragment viewDays;
    ArrayList<Day> daysWithEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMonths = new ViewCalendarMonthFragment(Year.now());
        daysWithEvents = new ArrayList<>();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerCalendar, viewMonths)
                .addToBackStack("months")
                .commit();
    }

    @Override
    public void selectedDay(LocalDate date) {
        boolean dayExists = false;
        Day currentDay;
        for (int i = 0; i < daysWithEvents.size(); i ++) {
            currentDay = daysWithEvents.get(i);
            if (currentDay.getDate().equals(date)) {
                dayExists = true;
                break;
            }
        }
        getSupportFragmentManager().popBackStackImmediate();
        if (!dayExists) {
            currentDay = new Day(date);
            daysWithEvents.add(currentDay);
        }
        viewDays = new ViewCalendarDayFragment(); /// NEED TO ADD currentDay to constructor
        getSupportFragmentManager().beginTransaction()
                .add(viewDays, "day")
                .addToBackStack("day")
                .commit();
    }
}