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

public class PredefRutines extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predef_rutines);

        WorkOutDBHelper mDBHelper = new WorkOutDBHelper(this);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        listView = findViewById(R.id.listViewRoutinesPredef);

        final ArrayList<Routine> rutinas = mDBHelper.displayDataBaseInfoPredef();

        final AdapterRoutine adapterRoutine = new AdapterRoutine(this, 0 , rutinas);
        listView.setAdapter(adapterRoutine);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Routine routine = adapterRoutine.getItem(position);
                Log.i("Id: ", "ID DE LA RUTINA SELECCIONADA " + routine.getId_Routine());
                changeActivity(routine.getId_Routine());
            }
        });

    }

    public void changeActivity(int id){

        Intent i = new Intent(getApplicationContext(), WorkOutRoutine.class);
        i.putExtra("IdRoutine", id);
        startActivity(i);
    }
}
