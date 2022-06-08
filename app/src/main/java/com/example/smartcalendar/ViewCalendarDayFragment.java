package com.example.smartcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class ViewCalendarDayFragment extends Fragment implements EventAdapter.IEventSelected, View.OnClickListener {

    private TextView textViewHeader;
    private TextView textViewDay1;
    private TextView textViewDay2;
    private TextView textViewDay3;
    private TextView textViewDay4;
    private TextView textViewDay5;
    private TextView textViewDay6;
    private TextView textViewDay7;
    private RecyclerView recyclerViewEvents;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Button buttonAddEvent;
    private EventAdapter eventAdapter;
    private IFromViewCalendarDay sendData;
    private EventList allEvents;
    private Calendar currentDay;
    private static final String[] monthStrings = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};

    public ViewCalendarDayFragment() {
        // Required empty public constructor
    }

    public ViewCalendarDayFragment(EventList allEvents, Calendar startDay) {
        this.allEvents = allEvents;
        this.currentDay = startDay;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof IFromViewCalendarDay) {
            sendData = (IFromViewCalendarDay) context;
        } else {
            throw new RuntimeException(context + " must implement IFromViewCalendarDay");
        }
        super.onAttach(context);
    }

    public static ViewCalendarDayFragment newInstance(String param1, String param2) {
        return new ViewCalendarDayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_calendar_day, container, false);
        textViewHeader = rootView.findViewById(R.id.textViewDay);
        textViewDay1 = rootView.findViewById(R.id.textViewDayDay1);
        textViewDay2 = rootView.findViewById(R.id.textViewDayDay2);
        textViewDay3 = rootView.findViewById(R.id.textViewDayDay3);
        textViewDay4 = rootView.findViewById(R.id.textViewDayDay4);
        textViewDay5 = rootView.findViewById(R.id.textViewDayDay5);
        textViewDay6 = rootView.findViewById(R.id.textViewDayDay6);
        textViewDay7 = rootView.findViewById(R.id.textViewDayDay7);
        buttonAddEvent = rootView.findViewById(R.id.buttonAddEvent);
        recyclerViewEvents = rootView.findViewById(R.id.recyclerViewEvents);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewEvents.setLayoutManager(recyclerViewLayoutManager);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        String headerText = monthStrings[currentDay.get(Calendar.MONTH)] + " "
                + currentDay.get(Calendar.DAY_OF_MONTH) + " Events";
        textViewHeader.setText(headerText);
        int dayNumber = currentDay.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentDay.get(Calendar.DAY_OF_WEEK);
        Log.d("debug", "onStart: dayNumber: " + dayNumber + " dayOfWeek: " + dayOfWeek);
        String [] textNumbers = new String[7];

        for (int i = dayNumber - dayOfWeek; i < dayNumber - dayOfWeek + 7; i ++) {
            textNumbers[i - (dayNumber - dayOfWeek)] = "" + i;
        }
        textViewDay1.setText(textNumbers[0]);
        textViewDay2.setText(textNumbers[1]);
        textViewDay3.setText(textNumbers[2]);
        textViewDay4.setText(textNumbers[3]);
        textViewDay5.setText(textNumbers[4]);
        textViewDay6.setText(textNumbers[5]);
        textViewDay7.setText(textNumbers[6]);

        eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
        recyclerViewEvents.setAdapter(eventAdapter);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData.selectedAddEvent(currentDay);
            }
        });

        textViewDay1.setOnClickListener(this);
        textViewDay2.setOnClickListener(this);
        textViewDay3.setOnClickListener(this);
        textViewDay4.setOnClickListener(this);
        textViewDay5.setOnClickListener(this);
        textViewDay6.setOnClickListener(this);
        textViewDay7.setOnClickListener(this);
    }

    @Override
    public void eventSelected(Event event) {
        sendData.selectedEvent(event);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.textViewDayDay1:
                setAllNumbersToPlain();
                textViewDay1.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.SUNDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
            case R.id.textViewDayDay2:
                setAllNumbersToPlain();
                textViewDay2.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
            case R.id.textViewDayDay3:
                setAllNumbersToPlain();
                textViewDay3.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.TUESDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
            case R.id.textViewDayDay4:
                setAllNumbersToPlain();
                textViewDay4.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.WEDNESDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
            case R.id.textViewDayDay5:
                setAllNumbersToPlain();
                textViewDay5.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.THURSDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
            case R.id.textViewDayDay6:
                setAllNumbersToPlain();
                textViewDay6.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
            case R.id.textViewDayDay7:
                setAllNumbersToPlain();
                textViewDay7.setTypeface(null, Typeface.BOLD);
                currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), Calendar.SATURDAY);
                eventAdapter = new EventAdapter(allEvents.getAllEventsAtDay(currentDay), this);
                recyclerViewEvents.setAdapter(eventAdapter);
                break;
        }
    }

    private void setAllNumbersToPlain() {
        textViewDay1.setTypeface(null, Typeface.NORMAL);
        textViewDay2.setTypeface(null, Typeface.NORMAL);
        textViewDay3.setTypeface(null, Typeface.NORMAL);
        textViewDay4.setTypeface(null, Typeface.NORMAL);
        textViewDay5.setTypeface(null, Typeface.NORMAL);
        textViewDay6.setTypeface(null, Typeface.NORMAL);
        textViewDay7.setTypeface(null, Typeface.NORMAL);
    }

    public interface IFromViewCalendarDay {
        void selectedEvent(Event event);
        void selectedAddEvent(Calendar day);
    }
}