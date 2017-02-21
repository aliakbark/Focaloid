package com.example.aliakbar.focaloid.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aliakbar.focaloid.MainActivity;
import com.example.aliakbar.focaloid.R;


public class NotificationsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Notifications");

        return rootView;
    }

}