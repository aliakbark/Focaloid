package com.example.aliakbar.focaloid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.btn_register) Button btn_register;
    @BindView(R.id.fullname) EditText input_fullname;
    @BindView(R.id.email) EditText input_email;
    @BindView(R.id.password) EditText input_password;
    @BindView(R.id.cpassword) EditText input_cpassword;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    String gender=null;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnClick(R.id.btn_register)
    public void onClick_btn_register() {

        validation();

        String name = input_fullname.getText().toString().trim();
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String created_at = sdf.format(new Date());

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            registerUser(name, email, password, created_at);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }

    }



    public boolean validation(){

      boolean isValid=true;

        // Email validation

        String email1 = input_email.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email1.matches(emailPattern))
        {
            input_email.setError("Invalid email address");
            input_email.requestFocus();
            return isValid=false;
        }


        if (input_fullname.getText().toString().length()==0){
            input_fullname.setError("username not entered");
            input_fullname.requestFocus();
            return isValid=false;
        }

        else if (input_email.getText().toString().length()==0) {
            input_email.setError("email not entered");
            input_email.requestFocus();
            return isValid=false;
        }

        else if (input_password.getText().toString().length()==0){
            input_password.setError("password not entered");
            input_password.requestFocus();
            return isValid=false;
        }
        else if (input_cpassword.getText().toString().length()==0){
            input_cpassword.setError("password not re-entered");
            input_cpassword.requestFocus();
            return isValid=false;
        }

        else if(!input_password.getText().toString().equals(input_cpassword.getText().toString())) {
            input_cpassword.setError("Password Not matched");
            input_cpassword.requestFocus();
            return isValid=false;
        }

        else if (input_password.getText().toString().length()<8){

            input_password.setError("Password should be atleast of 8 charactors");
            input_password.requestFocus();
            return isValid=false;
        }
        return isValid;
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password,final String created_at) {



        db.addUser(name, email, password, created_at);
        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
// Launch login activity
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

//        // Tag used to cancel the request
//        String tag_string_req = "req_register";
//
//        pDialog.setMessage("Registering ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Method.POST,
//                AppConfig.URL_REGISTER, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    if (!error) {
//                        // User successfully stored in MySQL
//                        // Now store the user in sqlite
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
//                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
//
//                        // Launch login activity
//                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//
//                        // Error occurred in registration. Get the error
//                        // message
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
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
