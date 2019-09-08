package com.concertioApp.concertio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.concertioApp.com.concertioApp.viewAdapter.ConcertListAdapter;
import com.concertioApp.concertioFragments.DatePickFragment;

import java.util.LinkedList;

public class SearchConcerts extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String searchInput;
    String inputGenres;
    String inputDate;
    private RecyclerView mRecyclerView;
    private ConcertListAdapter mAdapter;
    private final LinkedList<String> wordList = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_concerts);

        //spinner setup
        Spinner spin = findViewById(R.id.genre_spinner);
        if (spin != null) {
            spin.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.genre_array, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spin != null){
            spin.setAdapter(spinAdapter);
        }

        for(int i = 0; i<100; i++){
            wordList.addLast(i +"holder");
        }

        mRecyclerView = findViewById(R.id.recyclerConcerts);
        mAdapter = new ConcertListAdapter(this, wordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //set search filter criteria for genre spinner, if default then set to empty string
        if(i != 0){
            inputGenres = adapterView.getItemAtPosition(i).toString();
        }
        else{
            inputGenres = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        inputGenres = "";
    }

    public void showDatePicker(View v){
        DialogFragment newFrag = new DatePickFragment();
        newFrag.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string   = Integer.toString(day);
        String year_string  = Integer.toString(year);
        inputDate  = (month_string +
                "/" + day_string + "/" + year_string);

        Toast.makeText(this, "Date: " + inputDate, Toast.LENGTH_SHORT).show();
    }
}
