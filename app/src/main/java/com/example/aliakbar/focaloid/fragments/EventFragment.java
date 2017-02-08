package com.example.aliakbar.focaloid.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aliakbar.focaloid.MainActivity;
import com.example.aliakbar.focaloid.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventFragment extends Fragment {


    EditText event_name;
    TextView event_date;
    TextView event_time;
    Spinner event_spinner;
    EditText event_comment;
    Button btn_cancel_event;
    Button btn_add_event;


    double houre,mini;
    String location;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int REQUEST_CODE=001;
    Calendar cal;

    FloatingActionButton fab_add_even;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Event");


        final Dialog dialog = new Dialog(rootView.getContext(), android.R.style.DeviceDefault_Light_ButtonBar);



//        event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                location=String.valueOf(event_spinner.getSelectedItem());
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        fab_add_even=(FloatingActionButton) rootView.findViewById(R.id.fab_add_event);
        fab_add_even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               Fragment fragment=new AddEventFragment();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.content_main,fragment,fragment.getClass().getSimpleName())
//                        .addToBackStack(null)
//                        .commit();

                dialog.setContentView(R.layout.dialog_add_event);
                dialog.show();

                event_date=(TextView) dialog.findViewById(R.id.event_date);
                event_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       final Calendar c = Calendar.getInstance();
                       mYear = c.get(Calendar.YEAR);
                       mMonth = c.get(Calendar.MONTH);
                       mDay = c.get(Calendar.DAY_OF_MONTH);


                       DatePickerDialog datePickerDialog = new DatePickerDialog(dialog.getContext(),new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                               event_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                           }
                       }, mYear, mMonth, mDay);
                       datePickerDialog.show();


                    }
                });

                event_time=(TextView) dialog.findViewById(R.id.event_time);
                event_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(dialog.getContext(),
                                new TimePickerDialog.OnTimeSetListener() {

                                    String time="A.M";
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        if(hourOfDay>=12) {
                                            if(hourOfDay>12)
                                                hourOfDay = hourOfDay - 12;
                                            if(hourOfDay==12)
                                                hourOfDay=12;
                                            time = "P.M";
                                        }


                                        event_time.setText(hourOfDay + ":" + minute+" "+time);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                event_spinner=(Spinner) dialog.findViewById(R.id.event_spinner);
                event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        location=String.valueOf(event_spinner.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                btn_add_event=(Button) dialog.findViewById(R.id.btn_add_event);
                btn_add_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        event_name=(EditText) dialog.findViewById(R.id.event_name);
                        String title = event_name.getText().toString();
                        event_comment=(EditText) dialog.findViewById(R.id.event_comment);
                        String comment = event_comment.getText().toString();

                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("beginTime", cal.getTimeInMillis());
                        intent.putExtra("allDay", false);
                        intent.putExtra("rrule", "FREQ=YEARLY");
                        intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                        intent.putExtra("title", title);
                        intent.putExtra("description", comment);
                        intent.putExtra("eventLocation", location);
                        event_name.clearComposingText();
                        event_comment.clearComposingText();
                        startActivity(intent);
                        Toast.makeText(dialog.getContext(),"Event added succesfully",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                btn_cancel_event=(Button) dialog.findViewById(R.id.btn_cancel_event);
                btn_cancel_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.cancel();
                    }
                });

            }
        });

        return rootView;
    }


    public EventFragment(){

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
