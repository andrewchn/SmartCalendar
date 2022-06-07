package com.example.smartcalendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCalendarDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCalendarDayFragment extends Fragment {

    public ViewCalendarDayFragment() {
        // Required empty public constructor
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
        View roootView = inflater.inflate(R.layout.fragment_view_calendar_day, container, false);
        return roootView;
    }
}