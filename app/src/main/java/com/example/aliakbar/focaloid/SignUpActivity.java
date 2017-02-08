package com.example.aliakbar.focaloid;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.btn_createaccount) Button btn_createaccount;
    @BindView(R.id.rg_gender) RadioGroup rg_gender;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.cpassword) EditText cpassword;


    String gender=null;
    int flag=0;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        db = this.openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS registration(username text,password text,email text,gender text)");

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id=rg_gender.getCheckedRadioButtonId();
                RadioButton selectedRB=(RadioButton) findViewById(id);
                Toast.makeText(getApplicationContext(), "hello"+selectedRB.getText(), Toast.LENGTH_SHORT).show();
                gender=selectedRB.getText().toString();
            }
        });

    }

    @OnClick(R.id.btn_createaccount)
    public void onClick_btn_createaccount() {

        validation();


            db.execSQL("INSERT INTO registration values('" + username.getText().toString() + "','" + password.getText().toString() + "','" + email.getText().toString() + "','" + gender + "');");
            Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_LONG).show();

            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);


    }



    public boolean validation(){

      boolean isValid=true;

        // Email validation

        String email1 = email.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email1.matches(emailPattern))
        {
            email.setError("Invalid email address");
            email.requestFocus();
            return isValid=false;
        }


        if (username.getText().toString().length()==0){
            username.setError("username not entered");
            username.requestFocus();
            return isValid=false;
        }

        else if (email.getText().toString().length()==0) {
            email.setError("email not entered");
            email.requestFocus();
            return isValid=false;
        }

        else if (password.getText().toString().length()==0){
            password.setError("password not entered");
            password.requestFocus();
            return isValid=false;
        }
        else if (cpassword.getText().toString().length()==0){
            cpassword.setError("password not re-entered");
            cpassword.requestFocus();
            return isValid=false;
        }

        else if(!password.getText().toString().equals(cpassword.getText().toString())) {
            cpassword.setError("Password Not matched");
            cpassword.requestFocus();
            return isValid=false;
        }

        else if (password.getText().toString().length()<8){

            password.setError("Password should be atleast of 8 charactors");
            password.requestFocus();
            return isValid=false;
        }
        return isValid;
    }


}
