package com.example.pccontrol;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.pccontrol.Systems.ConsoleManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinAppFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton actionButton;
    private LinearLayout actionListLay;

    private String mParam1;
    private String mParam2;

    public WinAppFragment() {
    }

    private CreateAction createActionFragment = new CreateAction();

    public static WinAppFragment newInstance(String param1, String param2) {
        WinAppFragment fragment = new WinAppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void init(View view){
        actionButton =  view.findViewById(R.id.createActionBut);
        actionListLay = view.findViewById(R.id.actionListenerLay);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_win_app, container, false);
        init(view);
        listenerAction();

        return view;
    }





    private void listenerAction(){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsoleManager.add("Test!", Color.GREEN);
                setNewFragment(createActionFragment);
            }
        });
    }




    private void setNewFragment(Fragment fragment) {
        if (getActivity() instanceof MainActivity){
             ((MainActivity) getActivity()).setNewFragment(fragment);
        }

    }

    private void addButt(){

        Button button = new Button(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT

        );

        params.setMargins(16,8,16,8);
        button.setLayoutParams(params);

        button.setText("Test!");
        button.setTextSize(16);
        button.setAllCaps(false);
        actionListLay.addView(button);
    }
}