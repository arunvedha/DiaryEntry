package com.example.android.diaryentry;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DailyEntryObject {

//    public class DailyEntry {
//        private String entryId;
//        private String title;
//        private String entry;
//        private Date dateCreated;
//        private Date dateModified;
//        private int mood;
//
//        public DailyEntry(){}
//
//        public DailyEntry(String title, String entry, int mood){
//            this.entry = entry;
//            this.title = title;
//            this.mood = mood;
//        }
//
//        public String getEntryId() {
//            return entryId;
//        }
//
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getEntry() {
//            return entry;
//        }
//
//
//
//        public Date getDateCreated() {
//            return dateCreated;
//        }
//
//
//
//        public Date getDateModified() {
//            return dateModified;
//        }
//
//
//
//        public int getMood() {
//            return mood;
//        }
//
//
//    }

    private List<DailyEntry> dailyEntry = new ArrayList<>() ;


    public DailyEntryObject(List<DailyEntry> dailyEntry) {
        this.dailyEntry = dailyEntry;
    }

    public List<DailyEntry> getDailyEntry() {
        return dailyEntry;
    }

    public void setDailyEntry(List<DailyEntry> dailyEntry) {
        this.dailyEntry = dailyEntry;
    }
}
