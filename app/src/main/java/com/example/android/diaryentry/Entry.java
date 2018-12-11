package com.example.android.diaryentry;

public class Entry {
    private String titleEntry;
    private String mood;
    private String entry;
    private String date;

    public Entry(String titleEntry,String mood,String entry,String date){
        this.titleEntry=titleEntry;
        this.mood = mood;
        this.entry = entry;
        this.date = date;
    }

    public String getTitleEntry(){
        return titleEntry;
    }

    public String getMood() {
        return mood;
    }

    public String getEntry() {
        return entry;
    }

    public String getDate() { return date; }
}
