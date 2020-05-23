package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.workoutlist.Data.WorkOutDBHelper;

import java.util.ArrayList;

public class CompletedRoutines extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_routines);

        WorkOutDBHelper mDBHelper = new WorkOutDBHelper(this);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        list = findViewById(R.id.listViewRoutinesCompleted);

        final ArrayList<Routine> rutinas = mDBHelper.displayDataBaseInfoCompleted();


        final AdapterRoutine adapterRoutine = new AdapterRoutine(this, 0 , rutinas);
        list.setAdapter(adapterRoutine);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Routine routine = adapterRoutine.getItem(position);
                Log.i("Id: ", "ID DE LA RUTINA SELECCIONADA " + routine.getId_Routine());
                changeActivity(routine.getId_Routine());
            }
        });
    }

    public void changeActivity(int id){

        Intent i = new Intent(getApplicationContext(), WorkOutCompletes.class);
        i.putExtra("IdRoutine", id);
        startActivity(i);
    }
}
