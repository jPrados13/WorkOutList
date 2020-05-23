package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutlist.Data.WorkOutDBHelper;

public class CreateRoutine extends AppCompatActivity {

    //View variable
    EditText routineName;
    TextView numberReps;
    Spinner routinetType;
    ImageButton plus;
    ImageButton less;
    Button add;

    //Routine variables
    String name = "";
    String status = "Haciendo";
    int number = 1;
    int stepsComp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        routineName = findViewById(R.id.editTextRoutineName);
        numberReps = findViewById(R.id.numberReps);
        routinetType = findViewById(R.id.spinnerType);
        plus = findViewById(R.id.buttonPlus);
        less = findViewById(R.id.buttonLess);
        add = findViewById(R.id.buttonSave);

        //create a list of items for the spinner.
        String[] types = new String[]{"Cardio", "Fitness"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        //set the spinners adapter to the previously created one.
        routinetType.setAdapter(adapterType);

        Toast toast = Toast.makeText(getApplicationContext(), "Primero elija el nombre y el número de ejercicios.", Toast.LENGTH_LONG);
        toast.show();

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActions(v);
            }
        };

        plus.setOnClickListener(listener);
        less.setOnClickListener(listener);
        add.setOnClickListener(listener);
    }

    public void onClickActions (View view){

        switch (view.getId()){

            case R.id.buttonPlus:
                increase();
                break;
            case R.id.buttonLess:
                decrease();
                break;
            case  R.id.buttonSave:
                if (validateRutine()){
                    callInsertNewRoutine();
                    Intent i = new Intent(getApplicationContext(), AddTask.class);
                    finish();
                    startActivity(i);
                }
                break;

        }
    }
    public void decrease (){
        if(number<=0){
            Toast toastMin = Toast.makeText(getApplicationContext(), "La rutina no puede tener menos de 1 ejercicio", Toast.LENGTH_SHORT);
            toastMin.show();
        }else{
            number--;
        }
        numberReps.setText(String.valueOf(number));
    }

    public void increase (){
        if(number>=20){
            Toast toastMax = Toast.makeText(getApplicationContext(), "Número máximo alcanzado", Toast.LENGTH_SHORT);
            toastMax.show();
        }else{
            number++;
        }

        numberReps.setText(String.valueOf(number));
    }

    public boolean validateRutine(){

        name = String.valueOf(routineName.getText());
        boolean valid = true;
        if (name.length()<1){
            valid = false;
            Toast toastFalse = Toast.makeText(getApplicationContext(), "El nombre no puede quedar vacio", Toast.LENGTH_LONG);
            toastFalse.show();
        }else if (number<1){
            valid = false;
            Toast toastFalse = Toast.makeText(getApplicationContext(), "La rutina no puede tener menos de 1 ejercicio", Toast.LENGTH_LONG);
            toastFalse.show();
        }
        return valid;
    }

    public void callInsertNewRoutine(){
        Routine rutina = new Routine(name,status, number, stepsComp, routinetType.getSelectedItem().toString());
        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);
        double insert = dbHelper.insertRoutine(rutina);
        /*Toast toastTrue = Toast.makeText(getApplicationContext(), "Rutina añadida en la tabla. Id: " +  insert, Toast.LENGTH_LONG);
        toastTrue.show();*/
        Log.i("Id nueva rutina: ", "" + insert);
    }
}
