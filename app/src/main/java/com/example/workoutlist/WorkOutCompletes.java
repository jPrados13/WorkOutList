package com.example.workoutlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.workoutlist.Data.WorkOutDBHelper;

import java.util.ArrayList;

public class WorkOutCompletes extends AppCompatActivity {

    ListView listTasks;
    ImageButton delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out_completes);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("IdRoutine", 0);

        final WorkOutDBHelper mDBHelper = new WorkOutDBHelper(this);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        listTasks = findViewById(R.id.listViewTasksComplete);
        delete = findViewById(R.id.imageButtonDeleteComplete);

        final ArrayList<Task> tasks = mDBHelper.displayWorkOutRoutine(id);

        final AdapterTask adapterTasks = new AdapterTask(this, 0 , tasks);
        listTasks.setAdapter(adapterTasks);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(WorkOutCompletes.this);
                alerta.setMessage("¿Está seguro de que desea borrar esta rutina?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDBHelper.deleteRoutine(id);
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Borrar Rutina");
                titulo.show();
            }
        });

    }
}
