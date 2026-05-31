package com.example.pccontrol;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pccontrol.Utils.ActionItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAction extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CreateAction() {
    }

    private SharedPreferences preferences;

    private List<ActionItem> actionItemList = new ArrayList<>();


    public static CreateAction newInstance(String param1, String param2) {
        CreateAction fragment = new CreateAction();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void init(View view){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        preferences = requireContext().getSharedPreferences("actions",MODE_PRIVATE);

    }

    private void saveActions(){
        Gson gson = new Gson();
        String json = gson.toJson(actionItemList);
        preferences.edit().putString("action_list",json).apply();

    }

    private void loadActions(){
        Gson gson = new Gson();
        String json = preferences.getString("action_list","[]");
        ActionItem[] items = gson.fromJson(json, ActionItem[].class);
        actionItemList.clear();
        actionItemList.addAll(Arrays.asList(items));

        //создаем кнопки
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_action, container, false);
        init(view);

        return view;
    }
}