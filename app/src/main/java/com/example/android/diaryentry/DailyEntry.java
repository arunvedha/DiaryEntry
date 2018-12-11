package com.example.android.diaryentry;

import java.sql.Date;

public class DailyEntry {
    private String entryId;
    private String title;
    private String entry;
    private String dateCreated;
    private int mood;

    public DailyEntry(){}

    public DailyEntry(String title, String entry, int mood,String dateCreated){
        this.entry = entry;
        this.title = title;
        this.mood = mood;
        this.dateCreated = dateCreated;
    }

    public String getEntryId() {
        return entryId;
    }


    public String getTitle() {
        return title;
    }

    public String getEntry() {
        return entry;
    }



    public String getDateCreated() {
        return dateCreated;
    }




    public int getMood() {
        return mood;
    }


}
