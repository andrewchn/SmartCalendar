package com.example.smartcalendar.ViewCalendar;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.smartcalendar.CloudGetter;
import com.example.smartcalendar.Event;
import com.example.smartcalendar.R;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewCalendarDayFragment extends Fragment implements EventAdapter.IEventSelected, CloudGetter.IFromCloudGetter {

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
    private Calendar currentDay;
    private TextView[] textViewDays;
    private static final String[] monthStrings = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
    private CloudGetter cloudGetter;


    public ViewCalendarDayFragment(Calendar startDay) {
        this.currentDay = startDay;
        this.cloudGetter = new CloudGetter(this);
        currentDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH), 0, 0);
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
        textViewDays = new TextView[]{textViewDay1, textViewDay2, textViewDay3, textViewDay4, textViewDay5, textViewDay6, textViewDay7};
        buttonAddEvent = rootView.findViewById(R.id.buttonAddEvent);
        recyclerViewEvents = rootView.findViewById(R.id.recyclerViewEvents);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewEvents.setLayoutManager(recyclerViewLayoutManager);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateHeader();
        int dayNumber = currentDay.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentDay.get(Calendar.DAY_OF_WEEK);
        cloudGetter.getAllEventsAtDay(currentDay);

        for (int i = 0; i < 7; i ++) {
            int tvNumber = i + 1 - dayOfWeek + dayNumber;
            String numberText = "" + tvNumber;
            if (tvNumber < 1)
                numberText = "";
            textViewDays[i].setText(numberText);
            if (dayNumber == tvNumber)
                textViewDays[i].setTypeface(null, Typeface.BOLD);
            Fragment parentFragment = this;
            int currentDayOfWeek = i;
            textViewDays[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAllNumbersToPlain();
                    textViewDays[currentDayOfWeek].setTypeface(null, Typeface.BOLD);
                    currentDay.setWeekDate(currentDay.getWeekYear(), currentDay.get(Calendar.WEEK_OF_YEAR), currentDayOfWeek + 1);
                    currentDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH), 0, 0);
                    cloudGetter.getAllEventsAtDay(currentDay);
                }
            });
        }

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData.selectedAddEvent(currentDay);
            }
        });
    }

    @Override
    public void eventSelected(Event event) {
        sendData.selectedEvent(event);
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

    private void updateHeader() {
        String headerText = monthStrings[currentDay.get(Calendar.MONTH)] + " "
                + currentDay.get(Calendar.DAY_OF_MONTH) + " Events";
        textViewHeader.setText(headerText);
    }

    @Override
    public void finishedGetEventList(ArrayList<Event> eventList) {
        eventAdapter = new EventAdapter(eventList, this);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    public interface IFromViewCalendarDay {
        void selectedEvent(Event event);
        void selectedAddEvent(Calendar day);
    }
}