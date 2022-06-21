package com.example.smartcalendar.ViewCalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.Event;
import com.example.smartcalendar.R;
import com.example.smartcalendar.SmartEvent;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    private ArrayList<Event> events;
    private IEventSelected sendData;

    public EventAdapter(ArrayList<Event> events, Fragment parentFragment) {
        if (parentFragment instanceof IEventSelected) {
            sendData = (IEventSelected) parentFragment;
        } else {
            throw new RuntimeException(parentFragment + " must implement IEventSelected");
        }
        this.events = events;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView time;
        private final TextView location;
        private final TextView smartEvent;
        private final CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textViewEventTitle);
            this.time = itemView.findViewById(R.id.textViewEventTime);
            this.location = itemView.findViewById(R.id.textViewEventLocation);
            this.smartEvent = itemView.findViewById(R.id.textViewSmartEventText);
            this.card = itemView.findViewById(R.id.cardViewEvent);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getTime() {
            return time;
        }

        public TextView getLocation() {
            return location;
        }

        public TextView getSmartEvent() {
            return smartEvent;
        }

        public CardView getCard() {
            return card;
        }
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.getTitle().setText(event.getTitle());
        holder.getTime().setText(event.getStringDuration());
        if (!event.hasLocation()) {
            holder.getLocation().setVisibility(View.GONE);
        } else {
            holder.getLocation().setVisibility(View.VISIBLE);
            holder.getLocation().setText(event.getLocation());
        }
        if (event instanceof SmartEvent) {
            holder.getSmartEvent().setVisibility(View.VISIBLE);
        } else {
            holder.getSmartEvent().setVisibility(View.GONE);
        }
        holder.getCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData.eventSelected(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface IEventSelected {
        void eventSelected(Event event);
    }
}
