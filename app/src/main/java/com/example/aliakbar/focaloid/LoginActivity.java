package com.example.aliakbar.focaloid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_signup)
    TextView text_joinus;
    @BindView(R.id.tv_forgotpass)
    TextView text_fpassword;
    @BindView(R.id.et_email)
    EditText input_email;
    @BindView(R.id.et_password)
    EditText input_password;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    SQLiteDatabase sdb=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (!checkPermission()) {
//            Toast.makeText(LoginActivity.this,"Permissions not granted",Toast.LENGTH_SHORT).show();
            requestPermission();
        }

    }

    @OnClick(R.id.btn_login)
    public void onClick_btn_login() {
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();

        int flag=0;

        sdb= SQLiteDatabase.openDatabase("data/data/com.example.aliakbar.focaloid/databases/focaloid_ldb",null,SQLiteDatabase.OPEN_READONLY);
        String s="SELECT email,password FROM user";
        Cursor c=sdb.rawQuery(s,null);

        if (c!=null){
            if (c.moveToFirst()){
                do {
                    String user_db=c.getString(c.getColumnIndex("email"));
                    Log.e("email",user_db);
                    String pass_db=c.getString(c.getColumnIndex("password"));
                    Log.e("password",pass_db);

                    if (user_db.equals(email)&&pass_db.equals(password)) {
                        flag = 1;
                    }

                }while (c.moveToNext());
            }
        }
        if (flag==1){


            Toast.makeText(LoginActivity.this,"Thanks",Toast.LENGTH_LONG).show();
            final Intent intent12= new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent12);
            finish();

        }
        else {
            input_email.setError("Invalid username or password");
            input_email.requestFocus();
        }
    }

    @OnClick(R.id.tv_signup)
    public void onClick_text_joinus() {
        Intent in=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(in);
    }

    @OnClick(R.id.tv_forgotpass)
    public void onClick_text_fpassword() {
        Intent in=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(in);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean externalStorageAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && externalStorageAccepted)
                        Toast.makeText(LoginActivity.this,"Permission Granted, Now you can access location data,external storage and camera.",Toast.LENGTH_LONG).show();
                       // Snackbar.make(view, "Permission Granted, Now you can access location data,external storage and camera.", Snackbar.LENGTH_LONG).show();
                    else {
                        Toast.makeText(LoginActivity.this,"Permission Denied, You cannot access location data,external storage and camera",Toast.LENGTH_LONG).show();
                      //  Snackbar.make(view, "Permission Denied, You cannot access location data,external storage and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA,WRITE_EXTERNAL_STORAGE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {

        pDialog.setMessage("Logging in ...");
        showDialog();

        // Create login session
        session.setLogin(true);
        hideDialog();

//        // Tag used to cancel the request
//        String tag_string_req = "req_login";
//
//        pDialog.setMessage("Logging in ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Method.POST,
//                AppConfig.URL_LOGIN, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Login Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    // Check for error node in json
//                    if (!error) {
//                        // user successfully logged in
//                        // Create login session
//                        session.setLogin(true);
//
//                        // Now store the user in SQLite
//                        String uid = jObj.getString("uid");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//                        String created_at = user
//                                .getString("created_at");
//
//                        // Inserting row in users table
//                        db.addUser(name, email, uid, created_at);
//
//                        // Launch main activity
//                        Intent intent = new Intent(LoginActivity.this,
//                                MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", email);
//                params.put("password", password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
