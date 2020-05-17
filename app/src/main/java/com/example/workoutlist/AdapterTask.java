package com.example.workoutlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterTask extends ArrayAdapter<Task> {

    TextView name;
    TextView target;
    TextView current;
    ProgressBar progress;


    public AdapterTask(@NonNull Context context, int resource, @NonNull ArrayList<Task> ejers) {
        super(context, resource, ejers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View list_item = convertView;

        //Verificar si la lista está vacia, si lo esta la inflamos.
        if (list_item == null){
            list_item = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
        }


        Task currentTask = getItem(position);

        name = list_item.findViewById(R.id.textViewTaskName);
        name.setText(currentTask.getName());

        target = list_item.findViewById(R.id.textViewTargetReps);
        target.setText("Objetivo: " + String.valueOf(currentTask.getTargetReps()));

        current = list_item.findViewById(R.id.textViewCurrentReps);
        current.setText("Actual: " + String.valueOf(currentTask.getCurrentReps()));




        return list_item;
    }


}
