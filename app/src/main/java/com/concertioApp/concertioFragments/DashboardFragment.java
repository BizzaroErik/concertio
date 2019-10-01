package com.concertioApp.concertioFragments;


import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.concertioApp.concertio.MainActivity;
import com.concertioApp.concertio.R;
import com.concertioApp.concertio.SearchConcerts;

import java.io.File;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnTouchListener{
    private boolean isMenuVisible = false;
    static final int REQUEST_TAKE_PHOTO = 1;
    LinearLayout menuOptions;
    String currentPhotoPath;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView discover   = v.findViewById(R.id.discover_act);
        TextView concert    = v.findViewById(R.id.concert_act);
        TextView playlist   = v.findViewById(R.id.playlist_act);
        TextView camera     = v.findViewById(R.id.camera_act);
        TextView login      = v.findViewById(R.id.login_act);

        discover.setOnTouchListener(this);
        concert.setOnTouchListener(this);
        playlist.setOnTouchListener(this);
        camera.setOnTouchListener(this);

        menuOptions = v.findViewById(R.id.menu_options);
        final ImageView menu = v.findViewById(R.id.main_dropdown);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMenuVisible){
                    menuOptions.setVisibility(View.VISIBLE);
                    menu.setBackgroundColor(Color.parseColor("#777777"));
                }
                else{
                    menuOptions.setVisibility(View.INVISIBLE);
                    menu.setBackgroundColor(Color.TRANSPARENT);
                }
                isMenuVisible = !isMenuVisible;
            }
        });
        login.setOnTouchListener(this);
        return v;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            view.setBackgroundColor(Color.parseColor("#777777"));
            switch(view.getId()){
                case R.id.discover_act:
                    launchDiscoverActivity();
                    break;
                case R.id.playlist_act:
                    break;
                case R.id.concert_act:
                    launchConcertActivity();
                    break;
                case R.id.camera_act:
                    dispatchTakePictureIntent();
                    break;
                case R.id.login_act:
                    showLogin(view);
                default:
                    Log.d("onClick", "on click for fragment triggered");
            }
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT);
        }
            return true;
    }

    public void showLogin(View v){
        GoogleLogin newFrag = new GoogleLogin();
        newFrag.show(getActivity().getSupportFragmentManager(),"googleLogin");
    }

    //DISCOVER INTENT BELOW
    public void launchDiscoverActivity() {
        Intent concertActivity = new Intent(getActivity(), MainActivity.class);
        startActivity(concertActivity);
    }

    //CONCERT INTENT BELOW
    public void launchConcertActivity() {
        Intent concertActivity = new Intent(getActivity(), SearchConcerts.class);
        startActivity(concertActivity);
    }

    //PICTURE INTENT AND ACTIONS BELOW
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_"+ ".jpg";
            File imagePath = new File(getActivity().getFilesDir(), "images");
            photoFile = new File(imagePath, imageFileName);
            currentPhotoPath = photoFile.getAbsolutePath();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = getUriForFile(getActivity(),"com.concertioApp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void takePicture(View view) {
        dispatchTakePictureIntent();
    }
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        galleryAddPic();
    }*/

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
}
