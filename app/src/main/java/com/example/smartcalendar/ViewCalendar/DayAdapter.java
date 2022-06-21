package com.example.smartcalendar.ViewCalendar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;

import java.time.YearMonth;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private YearMonth month;
    private ISelectedDay sendData;
    private int dayOffset;

    public DayAdapter(YearMonth month, MonthAdapter monthAdapter) {
        this.month = month;
        this.dayOffset = month.atDay(1).getDayOfWeek().getValue() % 7;
        if (monthAdapter instanceof ISelectedDay) {
            sendData = (ISelectedDay) monthAdapter;
        } else {
            throw new RuntimeException(monthAdapter + " must implement ISelectedDay");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDayNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewDayNumber = itemView.findViewById(R.id.textViewDayNumber);
        }

        public TextView getTextViewDayNumber() {
            return textViewDayNumber;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_calendar_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < dayOffset) {
            holder.getTextViewDayNumber().setText("");
        } else {
            int dayNumber = position - dayOffset + 1;
            String dayNumberText = "" + (dayNumber);
            holder.getTextViewDayNumber().setText(dayNumberText);
            holder.getTextViewDayNumber().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendData.selectedDay(dayNumber, month);
                    Log.d("debug", "onClick: day: " + dayNumber);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return month.lengthOfMonth() + dayOffset;
    }

    public interface ISelectedDay {
        void selectedDay(int day, YearMonth month);
    }
}
