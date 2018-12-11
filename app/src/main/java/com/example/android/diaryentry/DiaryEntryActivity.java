package com.example.android.diaryentry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryEntryActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    DatabaseReference myRef_;
    private String LOG_TAG = "diary entry activity";
    private EditText titleText,diaryEntry;
    private FirebaseDatabase firebaseDatabase_;
    DailyEntryObject dailyEntryObject_;
    private int mood,sizeList;
    private List<DailyEntry> dailyEntryList;
    DailyEntry dailyEntry_;

    private Spinner mMoodSpinner;
    java.util.Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);
        setUpUIViews();

        Intent intent = getIntent();
        final int FLAG = intent.getIntExtra("flag",0);
        final int position = intent.getIntExtra("position",0);
         sizeList = intent.getIntExtra("size",0);
        Log.e("sherlockHolmes","yesterday was his birthday " + sizeList);
         currentTime = Calendar.getInstance().getTime();
        Log.e(LOG_TAG,"to print current date" + currentTime.toString());
        dailyEntryList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

            databaseReference.child("dailyEntry").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if(dataSnapshot.exists() && FLAG==1) {
                        List<DailyEntry> entriesList=new ArrayList<>();
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            DailyEntry dailyEntry = postSnapshot.getValue(DailyEntry.class);
                            entriesList.add(dailyEntry);
                        }


                        titleText.setText(entriesList.get(position).getTitle());
                        diaryEntry.setText(entriesList.get(position).getEntry());
                        sizeList = position;
                        Log.e("dbRef","yesterday was his birthday " + sizeList);



                    }
                    if (FLAG==0){
                        titleText.setText("");
                        diaryEntry.setText("");
                        Log.e("glag ref","yesterday was his birthday " + sizeList +" " + FLAG);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DiaryEntryActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });

        setupSpinner();

    }

    private void setUpUIViews(){
        titleText = findViewById(R.id.title_text);
        mMoodSpinner=findViewById(R.id.spinner_mood);
        diaryEntry = findViewById(R.id.diary_entry);
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter  moodSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_mood_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        moodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mMoodSpinner.setAdapter(moodSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mMoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.mood_good))) {
                        mood = 0;
                    } else if (selection.equals(getString(R.string.mood_bad))) {
                        mood = 2;
                    } else {
                       mood = 1;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mood = 1;
            }
        });
    }



    private void sendUserData(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        java.util.Date d = new java.util.Date();
        String dayOfTheWeek = sdf.format(d);
        String currentDateString = DateFormat.getDateInstance().format(new java.util.Date());
        String currentTimeString = DateFormat.getTimeInstance().format(new java.util.Date());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());
        DailyEntry dailyEntry = new DailyEntry(titleText.getText().toString(),diaryEntry.getText().toString(),mood,currentDateString +"\n" + currentTimeString);
        Log.e("diaryEntry","yesterday was his birthday " + currentDateString);
        dailyEntryList.add(dailyEntry);
        Log.e("sendUserData","yesterday was his birthday " + sizeList);
        myRef.child("dailyEntry").child(""+sizeList).setValue(dailyEntryList.get(0));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.save_exit:{
                sendUserData();
                Intent intent = new Intent(DiaryEntryActivity.this,SecondActivity.class);
                intent.putExtra("title",titleText.getText().toString());

                startActivity(intent);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }



}
