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

public class AdapterRoutine extends ArrayAdapter<Routine> {

    TextView name;
    TextView target;
    TextView current;
    ProgressBar progress;
    ImageView type;

    public AdapterRoutine(@NonNull Context context, int resource, @NonNull ArrayList<Routine> rutinas) {
        super(context, resource, rutinas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View list_item = convertView;

        //Verificar si la lista está vacia, si lo esta la inflamos.
        if (list_item == null){
            list_item = LayoutInflater.from(getContext()).inflate(R.layout.list_routine_item, parent, false);
        }

        Routine currentRoutine = getItem(position);

        name = list_item.findViewById(R.id.textViewEjerName);
        name.setText(currentRoutine.getName());

        target = list_item.findViewById(R.id.textViewTarget);
        target.setText("Objetivos: " + String.valueOf(currentRoutine.getNumberSteps()));

        current = list_item.findViewById(R.id.textViewCurrent);
        current.setText("Complidos:" + String.valueOf(currentRoutine.getStepsCompleted()));

        type = list_item.findViewById(R.id.imageViewList);

        progress = list_item.findViewById(R.id.progressBarRoutine);
        progress.setProgress(currentRoutine.getProgress());

        setStyle(currentRoutine);
        return list_item;
    }

    public void setStyle(Routine currentRoutine){
        if (currentRoutine.getType().equalsIgnoreCase("Fitness")){
            type.setImageResource(R.drawable.ic_recomended);
        }
        if (currentRoutine.getType().equalsIgnoreCase("Cardio")){
            type.setImageResource(R.drawable.ic_cardio);
        }
    }
}
