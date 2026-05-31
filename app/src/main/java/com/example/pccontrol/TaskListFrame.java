package com.example.pccontrol;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pccontrol.Adapter.ProcessAdapter;
import com.example.pccontrol.Adapter.ProcessInfo;
import com.example.pccontrol.Utils.ActionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFrame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFrame extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ProcessAdapter adapter;
    private List<ProcessInfo> processInfos = new ArrayList<>();





    private SwipeRefreshLayout swipeRefreshLayout;

    public TaskListFrame() {
        // Required empty public constructor
    }


    public static TaskListFrame newInstance(String param1, String param2) {
        TaskListFrame fragment = new TaskListFrame();
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
        recyclerView = view.findViewById(R.id.taskListsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.taskswipeRefresh);
        adapter = new ProcessAdapter(this.getContext(),processInfos);
        recyclerView.setAdapter(adapter);


        //testcode

        swipeRefreshLayout.setOnRefreshListener(()->{
            loadProcess();
        });

    }

    private void listener(){

    }


    private void loadProcess(){
        processInfos.clear();
        processInfos.add(new ProcessInfo("chrome.exe", 1234));
        processInfos.add(new ProcessInfo("explorer.exe", 5678));
        processInfos.add(new ProcessInfo("java.exe", 9012));
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_list_frame, container, false);
        init(view);
        listener();



        //begin

        return view;


    }
}