package com.example.android.diaryentry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private EntryAdapter entryAdapter;
    private ListView listView ;
    private String titleFb;
    private String moodString;
    ArrayList<Entry> entries;
    private ArrayList<Integer> integers;
    private int sizeList,FLAG=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query = getIntent().getStringExtra("query");
        integers=new ArrayList<>();
        Log.e("coding","sema " + query);
        entries = new ArrayList<Entry>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        integers = new ArrayList<Integer>();
        listView =  findViewById(R.id.list_search);
        listView.setBackgroundColor(getResources().getColor(R.color.magnitude3));

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.child("dailyEntry").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    final List<DailyEntry> entriesList = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        DailyEntry dailyEntry = postSnapshot.getValue(DailyEntry.class);
                        entriesList.add(dailyEntry);
                    }
                    sizeList = entriesList.size();

                    Log.e("SecondActivity", "seeeeeeeeee" + sizeList);
                    sizeList = entriesList.size();
                    for (int i = 0; i < sizeList; i++) {

                        titleFb = entriesList.get(i).getTitle();
                        if (titleFb.toLowerCase().trim().contains(query.toLowerCase().trim())){
//                            ArrayList<String> myList = new ArrayList<String>();
//                            intent.putExtra("mylist", myList);
//                            In the other Activity:
//
//                            ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
                            integers.add(i);
//                            int mood = entriesList.get(i).getMood();
//                        if (mood == 0) {
//                            moodString = "good";
//                        }
//
//                        if (mood == 1) {
//                            moodString = "normal";
//                        }
//
//                        if (mood == 2) {
//                            moodString = "bad";
//                        }

                        Log.e("SecondActivity", "sadasdsdasdad" + titleFb);
//                        entries.add(new Entry(titleFb, moodString, entriesList.get(i).getEntry()));
                        }}
                    Log.e("Integer", "seeeeeeeeee" + integers.size());


                        if (integers.size() == 0) {
                            Log.e("SecondActivity", "No match found" );
                            Toast.makeText(SearchActivity.this,"no results found", Toast.LENGTH_LONG).show();
                        } else {
                            for(int j=0;j<integers.size();j++) {
                                int mood = entriesList.get(integers.get(j)).getMood();
                                if (mood == 0) {
                                    moodString = "good";
                                }

                                if (mood == 1) {
                                    moodString = "normal";
                                }

                                if (mood == 2) {
                                    moodString = "bad";
                                }
                                entries.add(new Entry(entriesList.get(integers.get(j)).getTitle(), moodString, entriesList.get(integers.get(j)).getEntry()));
                            }
                            Log.e("Entries", "sizeeeeeee" + entries.size());
                            entryAdapter = new EntryAdapter(SearchActivity.this, entries);
                            Log.e("entries", " "+entries.size() );


                            listView.setAdapter(entryAdapter);
                        }




                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            Intent intent = new Intent(SearchActivity.this,DiaryEntryActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("flag",FLAG);
                            intent.putExtra("position",integers.get(position));
                            intent.putExtra("size",sizeList);
                            Log.e("intentActivity", "seeeeeeeeee" + sizeList);
                            startActivity(intent);
                        }
                    });



                }


            }
            //

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
