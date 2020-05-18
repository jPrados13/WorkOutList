package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    }
}
