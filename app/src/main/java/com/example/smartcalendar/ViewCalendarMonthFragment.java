package com.example.smartcalendar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.time.LocalDate;
import java.time.Year;

public class ViewCalendarMonthFragment extends Fragment implements MonthAdapter.ISelectedDayInMonth {

    private Spinner spinnerYear;
    private RecyclerView recyclerViewMonths;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private MonthAdapter monthAdapter;
    private Year year;
    private IFromViewCalendarMonth sendData;

    public ViewCalendarMonthFragment() {
        // Required empty public constructor
    }

    public ViewCalendarMonthFragment(Year year) {
        this.year = year;
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

    public static ViewCalendarMonthFragment newInstance(String param1, String param2) {
        return new ViewCalendarMonthFragment();
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
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void selectedDay(LocalDate date) {
        sendData.selectedDay(date);
    }

    public void updateYear(Year year) {
        this.year = year;
    }

    public interface IFromViewCalendarMonth {
        void selectedDay(LocalDate date);
    }
}