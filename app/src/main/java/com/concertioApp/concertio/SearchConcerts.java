package com.concertioApp.concertio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.concertioApp.com.concertioApp.objectAdapter.ConcertItem;
import com.concertioApp.com.concertioApp.viewAdapter.ConcertListAdapter;
import com.concertioApp.concertioFragments.DatePickFragment;
import com.concertioApp.networkUtils.API;
import com.concertioApp.networkUtils.APIFactory;
import com.concertioApp.systemUtils.JSONtoConcertItems;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchConcerts extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String inputGenres;
    String inputDate;
    private RecyclerView mRecyclerView;
    private ConcertListAdapter mAdapter;
    private ArrayList<ConcertItem> eventList = new ArrayList<>();

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

        RequestQueue queue = Volley.newRequestQueue(this);

        APIFactory apiFactory = new APIFactory();
        API apss = apiFactory.getApi("EVENTS");
        String s = apss.buildDefaultURL();
        JsonObjectRequest jsonConcertRequest = new JsonObjectRequest(Request.Method.GET, s, null, new SearchConcerts.ResponseListener(), new SearchConcerts.ErrorListener());
        queue.add(jsonConcertRequest);

        mRecyclerView = findViewById(R.id.recyclerConcerts);
        mAdapter = new ConcertListAdapter(this, eventList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class ResponseListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            Log.d("RESPONSE", response.toString());

            //do the work to turn json into news blocks
            eventList = JSONtoConcertItems.convertToConcertList(response, eventList);
            mAdapter.notifyDataSetChanged();
        }
    }
    private class ErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error){
            Log.d("RESPONSE", error.toString());
        }
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
