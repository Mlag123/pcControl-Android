package com.example.pccontrol;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.pccontrol.Systems.ConsoleManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText ipAddressPlain;
    private EditText espIpAdressPlain;
    private EditText securePathPlain;
    private Switch use_https;
    private Switch allowFurry;

    private Button filePickerSecureFile;

    public SettingsFragment() {
    }

    public void init(View view) {
        ipAddressPlain = view.findViewById(R.id.ipAddressPlain);
        espIpAdressPlain = view.findViewById(R.id.espIpAddressPlain);
        securePathPlain = view.findViewById(R.id.securePathPlain);
        use_https = view.findViewById(R.id.useHttpSwitch);
        allowFurry = view.findViewById(R.id.furrySwitch);
    }


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        init(view);
        //begin
        listenerAction(view);


        return view;
    }

    public void listenerAction(View view){

        allowFurry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ConsoleManager.add("Hello furry!", Color.MAGENTA);
                }
            }
        });
    }
}