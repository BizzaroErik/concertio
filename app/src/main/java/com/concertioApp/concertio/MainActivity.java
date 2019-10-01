package com.concertioApp.concertio;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.concertioApp.com.concertioApp.objectAdapter.NewsItem;
import com.concertioApp.com.concertioApp.viewAdapter.NewsListAdapter;

import android.os.Bundle;
import com.concertioApp.concertioFragments.DashboardFragment;
import com.concertioApp.networkUtils.API;
import com.concertioApp.networkUtils.APIFactory;
import com.concertioApp.systemUtils.JSONtoNewsItems;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NewsItem> newsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    DashboardFragment dbf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);

        APIFactory apiFactory = new APIFactory();
        API apss = apiFactory.getApi("NEWS");
        String s = apss.buildDefaultURL();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, s, null, new ResponseListener(), new ErrorListener());
        queue.add(jsonRequest);

        mRecyclerView = findViewById(R.id.recyclerNews);
        mAdapter = new NewsListAdapter(this, newsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbf = new DashboardFragment();
    }

    private class ResponseListener implements Response.Listener<JSONObject>{
        @Override
        public void onResponse(JSONObject response) {
            //Log.d("RESPONSE", response.toString());
            //do the work to turn json into news blocks
            newsList = JSONtoNewsItems.convertToNewsList(response, newsList);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}