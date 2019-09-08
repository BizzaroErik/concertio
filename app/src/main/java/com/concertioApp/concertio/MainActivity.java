package com.concertioApp.concertio;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.concertioApp.com.concertioApp.objectAdapter.NewsItem;
import com.concertioApp.com.concertioApp.viewAdapter.NewsListAdapter;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import com.concertioApp.systemUtils.JSONtoNewsItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

import static androidx.core.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {
    private LinkedList<NewsItem> newsList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    String currentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);

        //Todo: make url dynamic, with todays date, and possibly add location data
        String url ="https://newsapi.org/v2/everything?q=Music&from=2019-09-08&apiKey=";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new ResponseListener(), new ErrorListener());
        queue.add(jsonRequest);

        mRecyclerView = findViewById(R.id.recyclerNews);
        mAdapter = new NewsListAdapter(this, newsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    private class ResponseListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            Log.d("RESPONSE", response.toString());

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




    //CONCERT INTENT BELOW
    public void launchConcertActivity(View view) {
        Intent concertActivity = new Intent(MainActivity.this, SearchConcerts.class);
        startActivity(concertActivity);

    }

    //PICTURE INTENT AND ACTIONS BELOW
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_"+ ".jpg";
            File imagePath = new File(this.getFilesDir(), "images");
            photoFile = new File(imagePath, imageFileName);
            currentPhotoPath = photoFile.getAbsolutePath();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = getUriForFile(this,"com.concertioApp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void takePicture(View view) {
        dispatchTakePictureIntent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        galleryAddPic();
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}

