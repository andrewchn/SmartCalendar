package com.example.smartcalendar.ViewCalendar;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.smartcalendar.R;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewCalendarMonthFragment extends Fragment implements MonthAdapter.ISelectedDayInMonth {

    private Spinner spinnerYear;
    private RecyclerView recyclerViewMonths;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private MonthAdapter monthAdapter;
    private Year year;
    private List<String> yearArray;
    private ArrayAdapter<String> yearAdapter;
    private IFromViewCalendarMonth sendData;
    private Fragment parentFragment = this;

    public ViewCalendarMonthFragment(Year year) {
        this.year = year;
        this.yearArray = new ArrayList<>();
        int currentYear = year.getValue();
        for (int i = currentYear - 5; i <= currentYear + 5; i ++) {
            yearArray.add("" + i);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof IFromViewCalendarMonth) {
            sendData = (IFromViewCalendarMonth) context;
        } else {
            throw new RuntimeException(context + " must implement IFromViewCalendarMonth");
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
        View rootView = inflater.inflate(R.layout.fragment_view_calendar_month, container, false);
        spinnerYear = rootView.findViewById(R.id.spinnerYear);
        recyclerViewMonths = rootView.findViewById(R.id.recyclerViewMonths);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMonths.setLayoutManager(recyclerViewLayoutManager);
        monthAdapter = new MonthAdapter(year, this);
        recyclerViewMonths.setAdapter(monthAdapter);
        yearAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, yearArray);
        spinnerYear.setAdapter(yearAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        int currentYearIndex = yearArray.indexOf("" + year.getValue());
        spinnerYear.setSelection(currentYearIndex);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedYear = Integer.parseInt(yearArray.get(position));
                int currentYear = year.getValue();
                long yearDiff = selectedYear - currentYear;
                year = year.plusYears(yearDiff);
                monthAdapter.updateYear(year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void selectedDay(Calendar date) {
        sendData.selectedDay(date);
    }

    public interface IFromViewCalendarMonth {
        void selectedDay(Calendar date);
    }
}