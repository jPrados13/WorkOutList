package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.workoutlist.Data.WorkOutDBHelper;

public class MainActivity extends AppCompatActivity {

    CardView currentRoutines;
    CardView completed;
    CardView predefine;
    CardView create;
    TextView nCurrents;
    TextView nCompleted;
    TextView nPredef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);

        nCompleted = findViewById(R.id.textViewNumberCompletes);
        nCurrents = findViewById(R.id.textViewNumberCurrents);
        nPredef = findViewById(R.id.textViewNumberPredefine);

        nCurrents.setText(String.valueOf(dbHelper.routinesOnGoing()));
        nCompleted.setText(String.valueOf(dbHelper.routinesCompleted()));
        nPredef.setText(String.valueOf(dbHelper.routinesPredefinidas()));


        currentRoutines = findViewById(R.id.cardViewCurrentRoutines);
        completed = findViewById(R.id.cardViewRoutinesCompleted);
        predefine = findViewById(R.id.cardViewExampleRoutines);
        create = findViewById(R.id.cardViewCreate);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(v);
            }
        };
        currentRoutines.setOnClickListener(listener);
        completed.setOnClickListener(listener);
        predefine.setOnClickListener(listener);
        create.setOnClickListener(listener);


    }

    public void changeActivity(View view){
        Intent i;
        switch (view.getId()){

            case R.id.cardViewCurrentRoutines:
                i = new Intent(this, CurrentRoutines.class);
                startActivity(i);
                break;
            case R.id.cardViewRoutinesCompleted:

                break;
            case  R.id.cardViewExampleRoutines:

                break;
            case  R.id.cardViewCreate:
                i = new Intent(this, CreateRoutine.class);
                startActivity(i);
                break;
        }
    }
}
