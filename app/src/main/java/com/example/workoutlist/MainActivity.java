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
    CardView predef;
    CardView create;
    TextView nCurrents;
    TextView nCompleted;
    TextView nPredef;


    int numberComp;
    int numberPredef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);

        nCompleted = findViewById(R.id.textViewNumberCompletes);
        nCurrents = findViewById(R.id.textViewNumberCurrents);
        nPredef = findViewById(R.id.textViewNumberPredef);


        numberComp = dbHelper.routinesCompleted();
        numberPredef = dbHelper.routinesPredef();

        nCurrents.setText(String.valueOf(dbHelper.routinesOnGoing()));
        nCompleted.setText(String.valueOf(numberComp));
        nPredef.setText(String.valueOf(numberPredef));



        currentRoutines = findViewById(R.id.cardViewCurrentRoutines);
        completed = findViewById(R.id.cardViewRoutinesCompleted);
        predef = findViewById(R.id.cardViewPredefRoutines);

        create = findViewById(R.id.cardViewCreate);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(v);
            }
        };
        currentRoutines.setOnClickListener(listener);
        completed.setOnClickListener(listener);
        predef.setOnClickListener(listener);
        create.setOnClickListener(listener);

        if (numberPredef==0){
            predef.setVisibility(View.GONE);
        }


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
            case R.id.cardViewPredefRoutines:
                if (numberPredef==0){
                    Toast toastTrue = Toast.makeText(getApplicationContext(), "Se han eliminado las rutinas predefinidas.", Toast.LENGTH_LONG);
                    toastTrue.show();
                }else{
                    i = new Intent(this, PredefRutines.class);
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
