package com.example.android.diaryentry;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DailyEntryObject {



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
