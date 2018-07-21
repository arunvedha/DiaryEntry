package com.example.android.diaryentry;

public class Entry {
    private String titleEntry;
    private String mood;
    String entry;

    public Entry(String titleEntry,String mood,String entry){
        this.titleEntry=titleEntry;
        this.mood = mood;
        this.entry = entry;
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
}
