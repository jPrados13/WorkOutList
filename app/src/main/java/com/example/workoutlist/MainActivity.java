package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutlist.Data.WorkOutDBHelper;

public class MainActivity extends AppCompatActivity {

    CardView currentRoutines;
    CardView completed;

    CardView create;
    TextView nCurrents;
    TextView nCompleted;


    int numberComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);

        nCompleted = findViewById(R.id.textViewNumberCompletes);
        nCurrents = findViewById(R.id.textViewNumberCurrents);


        numberComp = dbHelper.routinesCompleted();

        nCurrents.setText(String.valueOf(dbHelper.routinesOnGoing()));
        nCompleted.setText(String.valueOf(numberComp));



        currentRoutines = findViewById(R.id.cardViewCurrentRoutines);
        completed = findViewById(R.id.cardViewRoutinesCompleted);

        create = findViewById(R.id.cardViewCreate);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(v);
            }
        };
        currentRoutines.setOnClickListener(listener);
        completed.setOnClickListener(listener);

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
                if (numberComp==0){
                    Toast toastTrue = Toast.makeText(getApplicationContext(), "Aún no has completado ninguna rutina.", Toast.LENGTH_LONG);
                    toastTrue.show();
                }else{
                    i = new Intent(this, CompletedRoutines.class);
                    startActivity(i);
                }
                break;
            case  R.id.cardViewCreate:
                i = new Intent(this, CreateRoutine.class);
                startActivity(i);
                break;
        }
    }
}
