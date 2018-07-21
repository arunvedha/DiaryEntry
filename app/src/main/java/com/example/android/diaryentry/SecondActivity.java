package com.example.android.diaryentry;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    FloatingActionButton floatingActionButton;
    private DatabaseReference entryCloudEndPoint;
    private DatabaseReference mDatabase;
    private Button logout;
    EntryAdapter entryAdapter;
    TextView moodTextView;
    ListView listView ;
    String titleFb;
    String moodString;
    public Bundle getBundle = null;
    ArrayList<Entry> entries;
    int FLAG =0,sizeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


//        final String title = getIntent().getStringExtra("title");
//        Log.e("SecondActivity","sadasdsdasdad" + title);

        entries = new ArrayList<Entry>();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("tag",firebaseAuth.getCurrentUser().getEmail());
        floatingActionButton = findViewById(R.id.fab);
        listView =  findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);



//        if(title!=null) entries.add(new Entry(title)) ;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondActivity.this,DiaryEntryActivity.class);
                intent.putExtra("flag",FLAG);
                intent.putExtra("size",sizeList);

                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.child("dailyEntry").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                profileName.setText("Name: " + userProfile.getUserName());
//                profileAge.setText("Age: " + userProfile.getUserAge());
//                profileEmail.setText("Email: " + userProfile.getUserEmail());
                if(dataSnapshot.exists()) {

//                    DailyEntry dailyEntry = dataSnapshot.getValue(DailyEntry.class);
                    final List<DailyEntry> entriesList = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        DailyEntry dailyEntry = postSnapshot.getValue(DailyEntry.class);
                        entriesList.add(dailyEntry);
                    }
                    sizeList = entriesList.size();
//                    titleText.setText(dailyEntry.getTitle());
//                    diaryEntry.setText(dailyEntry.getEntry());
                    Log.e("SecondActivity", "seeeeeeeeee" + sizeList);
                    sizeList = entriesList.size();
                    for (int i = 0; i < sizeList; i++) {

                        titleFb = entriesList.get(i).getTitle();
                    int mood = entriesList.get(i).getMood();
                    if (mood == 0) {
                        moodString = "good";

                    }

                    if (mood == 1) {
                        moodString = "normal";
                    }

                    if (mood == 2) {
                        moodString = "bad";
                    }

                    Log.e("SecondActivity", "sadasdsdasdad" + titleFb);
                    entries.add(new Entry(titleFb, moodString, entriesList.get(i).getEntry()));

                    if (entries.size() == 0) {
                        Log.e("SecondActivity", "if else loop" + entries.size());
                        entryAdapter = new EntryAdapter(SecondActivity.this);
                    } else
                        entryAdapter = new EntryAdapter(SecondActivity.this, entries);
                    listView.setAdapter(entryAdapter);

                }



                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            FLAG =1;

                            Intent intent = new Intent(SecondActivity.this,DiaryEntryActivity.class);
                            intent.putExtra("flag",FLAG);
                            intent.putExtra("position",position);
                            intent.putExtra("size",sizeList);
                            Log.e("intentActivity", "seeeeeeeeee" + sizeList);
                            startActivity(intent);
                        }
                    });

//                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                        @Override
//                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                               databaseReference.child("dailyEntry").child(""+position).setValue(null);
//                               entryAdapter.clear();
//
//                            for (int i = 0; i < sizeList; i++) {
//
//                                titleFb = entriesList.get(i).getTitle();
//                                int mood = entriesList.get(i).getMood();
//                                if (mood == 0) {
//                                    moodString = "good";
//                                }
//
//                                if (mood == 1) {
//                                    moodString = "normal";
//                                }
//
//                                if (mood == 2) {
//                                    moodString = "bad";
//                                }
//
//                                Log.e("SecondActivity", "sadasdsdasdad" + titleFb);
//                                entries.add(new Entry(titleFb, moodString, entriesList.get(i).getEntry()));
//
//                                if (entries.size() == 0) {
//                                    Log.e("SecondActivity", "if else loop" + entries.size());
//                                    entryAdapter = new EntryAdapter(SecondActivity.this);
//                                } else
//                                    entryAdapter = new EntryAdapter(SecondActivity.this, entries);
//                                listView.setAdapter(entryAdapter);
//
//                            }
//                            return false;
//                        }
//                    });

                }


            }
            //

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

            if(entries.size()==0) {
                Log.e("SecondActivity", "if else loop" + entries.size());
                entryAdapter = new EntryAdapter(this);
            }
            else
                 entryAdapter = new EntryAdapter(this, entries);
            listView.setAdapter(entryAdapter);




    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search_track);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(SecondActivity.this,SearchActivity.class);
                intent.putExtra("query",query);
                intent.putExtra("size",sizeList);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }




}
