package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.workoutlist.Data.WorkOutDBHelper;

import java.util.ArrayList;

public class CurrentRoutines extends AppCompatActivity {

    ListView listRoutine;
    ImageView backg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_routines);

        WorkOutDBHelper mDBHelper = new WorkOutDBHelper(this);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        listRoutine = findViewById(R.id.listViewRoutines);
        backg = findViewById(R.id.imageViewBackg);


        final ArrayList<Routine> rutinas = mDBHelper.displayDataBaseInfoCurrents();
        setBackground(rutinas);

        final AdapterRoutine adapterRoutine = new AdapterRoutine(this, 0 , rutinas);
        listRoutine.setAdapter(adapterRoutine);

        listRoutine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Routine routine = adapterRoutine.getItem(position);
                Log.i("Id: ", "ID DE LA RUTINA SELECCIONADA " + routine.getId_Routine());
                changeActivity(routine.getId_Routine());
            }
        });

        backg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CreateRoutine.class);
                startActivity(i);
            }
        });

    }

    public void changeActivity(int id){

        Intent i = new Intent(getApplicationContext(), WorkOutRoutine.class);
        i.putExtra("IdRoutine", id);
        startActivity(i);
    }
    public void setBackground(ArrayList<Routine> rutinas){
        if (rutinas.isEmpty()){
            listRoutine.setVisibility(View.GONE);
            backg.setVisibility(View.VISIBLE);
        }else{
            listRoutine.setVisibility(View.VISIBLE);
            backg.setVisibility(View.GONE);
        }
    }
}
