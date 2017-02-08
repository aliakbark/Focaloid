package com.example.aliakbar.focaloid;

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
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password)
    EditText password;

    SQLiteDatabase db=null;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String uNAME = "usernameKey";
    public static final String pASSW = "passKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        sharedpreferences= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        final String username1=sharedpreferences.getString("usernameKey",null);
        username.setText(username1);

        if (!checkPermission()) {
//            Toast.makeText(LoginActivity.this,"Permissions not granted",Toast.LENGTH_SHORT).show();
            requestPermission();
        }

    }

    @OnClick(R.id.btn_login)
    public void onClick_btn_login() {
        int flag=0;
        final String name=username.getText().toString();
        final String pass=password.getText().toString();

        final Intent intent= new Intent(LoginActivity.this,MainActivity.class);

        db= SQLiteDatabase.openDatabase("data/data/com.example.aliakbar.focaloid/databases/mydb",null,SQLiteDatabase.OPEN_READONLY);
        String s="SELECT username,password FROM registration";
        Cursor c=db.rawQuery(s,null);

        if (c!=null){
            if (c.moveToFirst()){
                do {
                    String user_db=c.getString(c.getColumnIndex("username"));
                    Log.e("username",user_db);
                    String pass_db=c.getString(c.getColumnIndex("password"));
                    Log.e("password",pass_db);

                    if (user_db.equals(name)&&pass_db.equals(pass)) {
                        flag = 1;
                    }

                }while (c.moveToNext());
            }
        }
        if (flag==1){
            final SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(uNAME,name);
            editor.putString(pASSW,pass);
            editor.commit();
            Toast.makeText(LoginActivity.this,"Thanks",Toast.LENGTH_LONG).show();

            startActivity(intent);

        }
        else {
            username.setError("Invalid username or password");
            username.requestFocus();
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
}
