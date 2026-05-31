package com.example.pccontrol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pccontrol.R;

import java.util.List;

public class ProcessAdapter extends RecyclerView.Adapter<ProcessAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    private final List<ProcessInfo> process;


    public ProcessAdapter(Context context, List<ProcessInfo> process){
        this.process = process;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProcessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_process,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessAdapter.ViewHolder holder, int position) {
        ProcessInfo processInfo = process.get(position);
        holder.nameView.setText(processInfo.getName());
        holder.pidView.setText(String.valueOf(processInfo.getPid()));
    }

    @Override
    public int getItemCount() {
        return process.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView,pidView;

        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.processName);
            pidView = view.findViewById(R.id.processPid);
        }
    }
}
