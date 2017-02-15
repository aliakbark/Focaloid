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

    DBAdapter databaseAdapter;


    String gender=null;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        databaseAdapter = new DBAdapter(this);
        databaseAdapter = databaseAdapter.open();





    }

    @OnClick(R.id.btn_register)
    public void onClick_btn_register() {

        validation();

        String iname = input_fullname.getText().toString().trim();
        String iemail = input_email.getText().toString().trim();
        String ipassword = input_password.getText().toString().trim();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String created_at = sdf.format(new Date());

        if (!iname.isEmpty() && !iemail.isEmpty() && !ipassword.isEmpty()) {
            databaseAdapter.RegisterUser(iname, iemail, ipassword,created_at);
            Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(SignUpActivity.this, LoginActivity.class);
            SignUpActivity.this.finish();
            startActivity(mainIntent);
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



}
