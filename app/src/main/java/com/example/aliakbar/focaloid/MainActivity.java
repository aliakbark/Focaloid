package com.example.aliakbar.focaloid;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;;
import android.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aliakbar.focaloid.fragments.CameraFragment;
import com.example.aliakbar.focaloid.fragments.EventFragment;
import com.example.aliakbar.focaloid.fragments.HomeFragment;
import com.example.aliakbar.focaloid.fragments.ShareAppFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    CircleImageView imgProfile;
    Button btn_prof_cam;

    TextView user_name,user_email;

    private GoogleApiClient mGoogleApiClient;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;


    private static final int RESULT_LOAD_IMAGE=1000;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Focaloid Cam";

    private Uri fileUri; // file url to store image

    private static final String PREFS_NAME = "nameKey";
    private static final String PREF_EMAIL = "emailKey";
    private static final String PREF_IMAGE ="imageKey";

    private final String DefaultUnameValue = "Ali Akbar";
    private final String DefaultEmailValue = "aar8811@gmail.com";
    private final String DefaultImageValue = "R.drawable.profile_pic";

    public static final String MyPREFERENCES = "MyPrefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      SharedPreferences userPreferences= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        final String uName=userPreferences.getString(PREFS_NAME,DefaultUnameValue);
        final String uEmail=userPreferences.getString(PREF_EMAIL,DefaultEmailValue);
        final String uProfImage=userPreferences.getString(PREF_IMAGE,DefaultImageValue);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment=new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navView = navigationView.getHeaderView(0);
        btn_prof_cam=(Button) navView.findViewById(R.id.btn_prof_cam);
        imgProfile=(CircleImageView) navView.findViewById(R.id.img_profile);
        user_name=(TextView) navView.findViewById(R.id.tv_user_name);
        user_email=(TextView) navView.findViewById(R.id.tv_user_email);

        user_name.setText(uName);
        user_email.setText(uEmail);
        Glide.with(this)
                .load(uProfImage)
                .error(R.drawable.error)      // optional
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);


        btn_prof_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(MainActivity.this, btn_prof_cam);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                            if (id == R.id.prof_b_gallery) {

                                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, RESULT_LOAD_IMAGE);


                            } else if (id == R.id.prof_b_camera) {

                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                    // start the image capture Intent
                                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);


                            } else if (id == R.id.prof_b_remove) {

                                Toast.makeText(MainActivity.this, "hehe remove", Toast.LENGTH_SHORT).show();

                            } else {

                            }

                        popup.dismiss();
                        return true;
                    }
                });

                popup.show();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }


    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            fragment=new HomeFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        } else if (id == R.id.nav_camera) {


           fragment=new CameraFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        } else if (id == R.id.nav_event) {
            fragment=new EventFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();


        } else if (id == R.id.connect_us) {
            Intent in=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_logout) {
            g_signOut();

        } else if (id == R.id.nav_share) {
            fragment=new ShareAppFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        }
        else {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK&& null != data) {
                Uri selectedImage = data.getData();
                Glide.with(this)
                        .load(selectedImage)
                        .error(R.drawable.error)      // optional
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfile);

            } else if(requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK)
            {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                final Bitmap cameraImage = BitmapFactory.decodeFile(fileUri.getPath(), options);
                imgProfile.setImageBitmap(cameraImage);
            }
            else{
                Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong,Try again.", Toast.LENGTH_LONG).show();
        }

    }
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void g_signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent sIntent=new Intent(MainActivity.this,LoginActivity.class);
                        Toast.makeText(MainActivity.this, "Logout Successfull", Toast.LENGTH_LONG).show();
                        MainActivity.this.finish();
                        startActivity(sIntent);
                    }
                });
    }

}
