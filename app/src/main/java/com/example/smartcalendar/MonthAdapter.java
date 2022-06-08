package com.example.smartcalendar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> implements DayAdapter.ISelectedDay {
    private Year year;
    private ISelectedDayInMonth sendData;

    private static final String[] monthStrings = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};

    public MonthAdapter(Year year, Fragment parentFragment) {
        Log.d("debug", "Month Adapter Created");
        this.year = year;
        if (parentFragment instanceof ISelectedDayInMonth) {
            sendData = (ISelectedDayInMonth) parentFragment;
        } else {
            throw new RuntimeException(parentFragment + " must implement ISelectedDay");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recyclerViewDays;
        private final RecyclerView.LayoutManager recyclerViewLayoutManager;
        private final TextView monthText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("debug", "ViewHolder: Creating Month Card");
            this.recyclerViewDays = itemView.findViewById(R.id.recyclerViewDays);
            this.monthText = itemView.findViewById(R.id.textViewMonth);
            this.recyclerViewLayoutManager = new GridLayoutManager(itemView.getContext(), 7);
            this.recyclerViewDays.setLayoutManager(this.recyclerViewLayoutManager);
        }

        public RecyclerView getRecyclerViewDays() {
            return recyclerViewDays;
        }

        public TextView getMonthText() {
            return monthText;
        }
    }

    @NonNull
    @Override
    public MonthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("debug", "Month onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_calendar_month, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthAdapter.ViewHolder holder, int position) {
        holder.getMonthText().setText(monthStrings[position]);
        DayAdapter dayAdapter = new DayAdapter(year.atMonth(position + 1), this);
        holder.getRecyclerViewDays().setAdapter(dayAdapter);
    }

    @Override
    public int getItemCount() {
        Log.d("debug", "getItemCount: called");
        return 12;
    }

    @Override
    public void selectedDay(int day, YearMonth month) {
        Calendar calendarDay = Calendar.getInstance();
        calendarDay.set(year.getValue(), month.getMonthValue(), day);
        sendData.selectedDay(calendarDay);
    }

    public interface ISelectedDayInMonth {
        void selectedDay(Calendar date);
    }
}
