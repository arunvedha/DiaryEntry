package com.example.android.diaryentry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EntryAdapter extends ArrayAdapter<Entry> {

    public EntryAdapter(Activity context, ArrayList<Entry> EntryAdapters) {
        super(context, 0, EntryAdapters);
    }
    public EntryAdapter(Activity context) {
        super(context, 0);
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list = convertView;
        if(list == null) {
            list = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Entry currentEntry = getItem(position);
        TextView titleTextView = (TextView) list.findViewById(R.id.title_text_entry);
        titleTextView.setText(currentEntry.getTitleEntry());

        TextView mood = list.findViewById(R.id.mood_text);
        mood.setText(currentEntry.getMood());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        String currentDateString = DateFormat.getDateInstance().format(new Date());
        String currentTimeString = DateFormat.getTimeInstance().format(new Date());

        Log.e("date","chumma than "+ currentDateString );

        TextView dayTextView = list.findViewById(R.id.date);
        TextView dateTextView = list.findViewById(R.id.time);

        dayTextView.setText(dayOfTheWeek);
        dateTextView.setText(currentDateString);

        GradientDrawable moodCircle = (GradientDrawable) mood.getBackground();
        int result;

        if (currentEntry.getMood().equals("normal")) result=1;
        else if (currentEntry.getMood().equals("good"))result=0;
        else result=2;

        int magnitudeColor = getMagnitudeColor(result);

        moodCircle.setColor(magnitudeColor);

        TextView entryBrief = list.findViewById(R.id.entry_brief);
        entryBrief.setText(currentEntry.getEntry());

        return list;
    }

    private int getMagnitudeColor(int magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
                magnitudeColorResourceId = R.color.magnitude0;
                break;
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;

            default:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
        }

        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

}
