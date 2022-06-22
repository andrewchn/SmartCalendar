package com.example.smartcalendar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcalendar.ViewCalendar.ViewCalendarDayFragment;

import java.lang.reflect.Array;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditEventFragment extends Fragment implements CloudSender.IFromCloudSender {

    private EditText editTextTitle;
    private EditText editTextLocation;
    private Spinner spinnerStartYear;
    private Spinner spinnerStartMonth;
    private Spinner spinnerStartDay;
    private Spinner spinnerStartTime;
    private Spinner spinnerEndYear;
    private Spinner spinnerEndMonth;
    private Spinner spinnerEndDay;
    private Spinner spinnerEndTime;
    private TextView textViewAlert1;
    private TextView textViewAlert2;
    private Spinner spinnerAlert1;
    private Spinner spinnerAlert2;
    private Switch switchSmartEvent;
    private TextView textViewFrequency;
    private Spinner spinnerFrequencyScale;
    private Spinner spinnerFrequency;
    private TextView textViewFrequencyText;
    private Button buttonSaveEvent;

    private Event event;
    private IFromEditEvent sendData;
    private boolean createNew = false;
    private List<String> yearArray;
    private ArrayAdapter<String> startYearArrayAdapter;
    private ArrayAdapter<String> endYearArrayAdapter;
    private List<String> monthArray;
    private ArrayAdapter<String> startMonthArrayAdapter;
    private ArrayAdapter<String> endMonthArrayAdapter;
    private List<String> startDayArray;
    private ArrayAdapter<String> startDayArrayAdapter;
    private List<String> endDayArray;
    private ArrayAdapter<String> endDayArrayAdapter;
    private List<String> timeArray;
    private ArrayAdapter<String> startTimeArrayAdapter;
    private ArrayAdapter<String> endTimeArrayAdapter;

    private CloudSender cloudSender;

    public EditEventFragment() {
        yearArray = new ArrayList<>();
        int now = Year.now().getValue();
        for (int i = now; i <= now + 5; i ++)
            yearArray.add("" + i);
        monthArray = new ArrayList<>();
        monthArray.add("January");
        monthArray.add("February");
        monthArray.add("March");
        monthArray.add("April");
        monthArray.add("May");
        monthArray.add("June");
        monthArray.add("July");
        monthArray.add("August");
        monthArray.add("September");
        monthArray.add("October");
        monthArray.add("November");
        monthArray.add("December");
        startDayArray = new ArrayList<>();
        endDayArray = new ArrayList<>();
        for (int i = 1; i <= 31; i ++) {
            startDayArray.add("" + i);
            endDayArray.add("" + i);
        }
        timeArray = new ArrayList<>();
        declareTimeArray();
        cloudSender = new CloudSender(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IFromEditEvent) {
            sendData = (IFromEditEvent) context;
        } else {
            throw new RuntimeException(context + " must implement IFromEditEvent");
        }
    }

    public static EditEventFragment newInstance(String param1, String param2) {
        return new EditEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_event, container, false);
        editTextTitle = rootView.findViewById(R.id.editTextEventTitle);
        editTextLocation = rootView.findViewById(R.id.editTextEventLocation);
        spinnerStartYear = rootView.findViewById(R.id.spinnerStartYear);
        spinnerStartMonth = rootView.findViewById(R.id.spinnerStartMonth);
        spinnerStartDay = rootView.findViewById(R.id.spinnerStartDay);
        spinnerStartTime = rootView.findViewById(R.id.spinnerStartTime);
        spinnerEndYear = rootView.findViewById(R.id.spinnerEndYear);
        spinnerEndMonth = rootView.findViewById(R.id.spinnerEndMonth);
        spinnerEndDay = rootView.findViewById(R.id.spinnerEndDay);
        spinnerEndTime = rootView.findViewById(R.id.spinnerEndTime);
        textViewAlert1 = rootView.findViewById(R.id.textViewAlert1);
        textViewAlert2 = rootView.findViewById(R.id.textViewAlert2);
        spinnerAlert1 = rootView.findViewById(R.id.spinnerAlert1);
        spinnerAlert2 = rootView.findViewById(R.id.spinnerAlert2);
        switchSmartEvent = rootView.findViewById(R.id.switchSmartEvent);
        textViewFrequency = rootView.findViewById(R.id.textViewFrquency);
        spinnerFrequencyScale = rootView.findViewById(R.id.spinnerFrequencyScale);
        spinnerFrequency = rootView.findViewById(R.id.spinnerFrequency);
        textViewFrequencyText = rootView.findViewById(R.id.textViewFrequencyText);
        buttonSaveEvent = rootView.findViewById(R.id.buttonSaveEvent);

        // hide all smart event features
        spinnerFrequency.setVisibility(View.GONE);
        spinnerFrequencyScale.setVisibility(View.GONE);
        textViewFrequency.setVisibility(View.GONE);
        textViewFrequencyText.setVisibility(View.GONE);

        // hide second alert
        textViewAlert2.setVisibility(View.GONE);
        spinnerAlert2.setVisibility(View.GONE);

        // declare array adapters
        startYearArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, yearArray);
        endYearArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, yearArray);
        startMonthArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, monthArray);
        endMonthArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, monthArray);
        startDayArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, startDayArray);
        endDayArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, endDayArray);
        startTimeArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, timeArray);
        endTimeArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.add_event_spinner_entry, timeArray);

        // set up spinners
        spinnerStartYear.setAdapter(startYearArrayAdapter);
        spinnerStartMonth.setAdapter(startMonthArrayAdapter);
        spinnerStartDay.setAdapter(startDayArrayAdapter);
        spinnerStartTime.setAdapter(startTimeArrayAdapter);
        spinnerEndYear.setAdapter(endYearArrayAdapter);
        spinnerEndMonth.setAdapter(endMonthArrayAdapter);
        spinnerEndDay.setAdapter(endDayArrayAdapter);
        spinnerEndTime.setAdapter(endTimeArrayAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        switchSmartEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinnerFrequency.setVisibility(View.VISIBLE);
                    spinnerFrequencyScale.setVisibility(View.VISIBLE);
                    textViewFrequency.setVisibility(View.VISIBLE);
                    textViewFrequencyText.setVisibility(View.VISIBLE);
                    startTimeArrayAdapter.clear();
                    endTimeArrayAdapter.clear();
                } else {
                    spinnerFrequency.setVisibility(View.GONE);
                    spinnerFrequencyScale.setVisibility(View.GONE);
                    textViewFrequency.setVisibility(View.GONE);
                    textViewFrequencyText.setVisibility(View.GONE);
                    declareTimeArray();
                    startTimeArrayAdapter.addAll(timeArray);
                    endTimeArrayAdapter.addAll(timeArray);
                }
            }
        });

        spinnerStartMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinnerStartYear.getSelectedItem().toString());
                YearMonth month = YearMonth.of(year, position + 1);
                startDayArray = getDayArray(month.lengthOfMonth());
                startDayArrayAdapter.clear();
                startDayArrayAdapter.addAll(startDayArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                startDayArray = new ArrayList<>();
                startDayArrayAdapter.clear();
                startDayArrayAdapter.addAll(startDayArray);
            }
        });

        spinnerEndMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinnerEndYear.getSelectedItem().toString());
                YearMonth month = YearMonth.of(year, position + 1);
                Log.d("debug", "onItemSelected: year: " + year);
                endDayArray = getDayArray(month.lengthOfMonth());
                endDayArrayAdapter.clear();
                endDayArrayAdapter.addAll(endDayArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                endDayArray = new ArrayList<>();
                endDayArrayAdapter.clear();
                endDayArrayAdapter.addAll(endDayArray);
            }
        });

        spinnerStartYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinnerStartYear.getSelectedItem().toString());
                int month = spinnerStartMonth.getSelectedItemPosition() + 1;
                YearMonth yearMonth = YearMonth.of(year, month);
                startDayArray = getDayArray(yearMonth.lengthOfMonth());
                startDayArrayAdapter.clear();
                startDayArrayAdapter.addAll(startDayArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                startDayArray = new ArrayList<>();
                startDayArrayAdapter.clear();
                startDayArrayAdapter.addAll(startDayArray);
            }
        });

        spinnerEndYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinnerEndYear.getSelectedItem().toString());
                int month = spinnerEndMonth.getSelectedItemPosition() + 1;
                YearMonth yearMonth = YearMonth.of(year, month);
                endDayArray = getDayArray(yearMonth.lengthOfMonth());
                endDayArrayAdapter.clear();
                endDayArrayAdapter.addAll(endDayArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                endDayArray = new ArrayList<>();
                endDayArrayAdapter.clear();
                endDayArrayAdapter.addAll(endDayArray);
            }
        });

        buttonSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String location = editTextLocation.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(getContext(), "Events must have a title", Toast.LENGTH_SHORT).show();
                } else {
                    // get all time information
                    Event newEvent;
                    Map<String, Calendar> selections = getTimeSelections();
                    if (createNew) {
                        newEvent = new Event(title, selections.get("start"), selections.get("end"));
                        if (!location.isEmpty())
                            newEvent.setLocation(location);
                        cloudSender.write(newEvent);
                    } else {
                        if (!location.isEmpty())
                            event.setLocation(location);
                        event.setStartDate(selections.get("start"));
                        event.setEndDate(selections.get("end"));
                        cloudSender.write(event);
                    }
                }
            }
        });
    }

    public void setEventToEdit(Event event) {
        this.event = event;
        createNew = false;
    }

    public void declareTimeArray() {
        for (int hour = 0; hour < 24; hour ++) {
            String hourString = "" + hour;
            boolean pm = false;
            if (hour == 0)
                hourString = "12";
            if (hour > 12) {
                hourString = "" + (hour - 12);
                pm = true;
            }
            for (int minute = 0; minute < 60; minute = minute + 30) {
                String minuteString = "" + minute;
                if (minute < 10)
                    minuteString = "0" + minute;
                String timeString = hourString + ":" + minuteString + " AM";
                if (pm)
                    timeString = hourString + ":" + minuteString + " PM";
                timeArray.add(timeString);
            }
        }
    }

    public ArrayList<String> getDayArray(int numdays) {
        ArrayList<String> daysArray = new ArrayList<>();
        for (int i = 1; i <= numdays; i ++) {
            daysArray.add("" + i);
        }
        return daysArray;
    }

    public void createNewEvent(Calendar day) {
        createNew = true;
    }

    private Map<String, Calendar> getTimeSelections() {
        Map<String, Calendar> selections = new HashMap<>();
        int startYear = Integer.parseInt(spinnerStartYear.getSelectedItem().toString());
        int startMonth = spinnerStartMonth.getSelectedItemPosition();
        int startDay = spinnerStartDay.getSelectedItemPosition() + 1;
        int [] startTimeArray = parseTime(spinnerStartTime.getSelectedItem().toString());
        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, startMonth, startDay, startTimeArray[0], startTimeArray[1]);
        int endYear = Integer.parseInt(spinnerEndYear.getSelectedItem().toString());
        int endMonth = spinnerEndMonth.getSelectedItemPosition();
        int endDay = spinnerEndDay.getSelectedItemPosition() + 1;
        int [] endTimeArray = parseTime(spinnerEndTime.getSelectedItem().toString());
        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, endMonth, endDay, endTimeArray[0], endTimeArray[1]);
        selections.put("start", startDate);
        selections.put("end", endDate);
        return selections;
    }

    private int[] parseTime(String time) {
        int[] timeArray = new int[2];
        String[] splitStartTimeColon = time.split(":");
        String[] splitStartTimeSpace = splitStartTimeColon[1].split(" ");
        String startTimeOfDay = splitStartTimeSpace[1];
        timeArray[0] = Integer.parseInt(splitStartTimeColon[0]);
        timeArray[1] = Integer.parseInt(splitStartTimeSpace[0]);
        if (startTimeOfDay.equals("PM"))
            timeArray[0] += 12;
        return timeArray;
    }

    @Override
    public void finishedWrite(boolean successful) {
        if (successful)
            sendData.saveEvent();
        else
            Toast.makeText(getContext(), "Unable to save event", Toast.LENGTH_SHORT).show();
    }

    public interface IFromEditEvent {
        void saveEvent();
    }
}