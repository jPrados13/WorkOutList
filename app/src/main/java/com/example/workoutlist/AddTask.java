package com.example.workoutlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutlist.Data.WorkOutDBHelper;

public class AddTask extends AppCompatActivity {

    //View variable
    EditText taskName;
    TextView numberReps;
    TextView numberSets;
    ImageButton plus;
    ImageButton less;
    ImageButton plusSets;
    ImageButton lessSets;
    Button add;

    //Task variables
    String name = "";
    String status = "Haciendo";
    int sets = 1;
    int targetReps = 1;
    int currentReps = 0;

    //Routine variables
    Integer[] info;
    int id;
    int nTask;

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);
        info = dbHelper.lastIdSteps();

        id = info[0];
        nTask = info[1];

        taskName = findViewById(R.id.editTextTaskName);
        numberReps = findViewById(R.id.numberRepsTask);
        numberSets = findViewById(R.id.numberSetsTask);
        plus = findViewById(R.id.buttonPlusReps);
        less = findViewById(R.id.buttonLessReps);
        plusSets = findViewById(R.id.buttonPlusSets);
        lessSets = findViewById(R.id.buttonLessSets);
        add = findViewById(R.id.buttonSaveTask);

        Toast toast = Toast.makeText(getApplicationContext(), "Ahora cree cada uno de los ejercicios.", Toast.LENGTH_LONG);
        toast.show();

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActions(v);
            }
        };

        plus.setOnClickListener(listener);
        less.setOnClickListener(listener);
        plusSets.setOnClickListener(listener);
        lessSets.setOnClickListener(listener);
        add.setOnClickListener(listener);
    }

    public void onClickActions (View view){

        switch (view.getId()){

            case R.id.buttonPlusReps:
                increaseReps();
                break;
            case R.id.buttonLessReps:
                decreaseReps();
                break;
            case R.id.buttonPlusSets:
                increaseSets();
                break;
            case R.id.buttonLessSets:
                decreaseSets();
                break;
            case  R.id.buttonSaveTask:
                if (validateTask()){
                    count++;
                        callInsertNewTask();
                        if (count == nTask){
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            finish();
                            startActivity(i);
                        }


                }
                break;

        }
    }
    public void decreaseReps (){
        if(targetReps<1){
            Toast toastMin = Toast.makeText(getApplicationContext(), "No puede ser menos de 1", Toast.LENGTH_SHORT);
            toastMin.show();
        }else{
            targetReps--;
        }
        numberReps.setText(String.valueOf(targetReps));
    }

    public void increaseReps (){
        if(targetReps>=99){
            Toast toastMax = Toast.makeText(getApplicationContext(), "Número máximo alcanzado", Toast.LENGTH_SHORT);
            toastMax.show();
        }else{
            targetReps++;
        }

        numberReps.setText(String.valueOf(targetReps));
    }

    public void decreaseSets (){
        if(sets<1){
            Toast toastMin = Toast.makeText(getApplicationContext(), "No puede ser menos de 1", Toast.LENGTH_SHORT);
            toastMin.show();
        }else{
            sets--;
        }
        numberSets.setText(String.valueOf(sets));
    }

    public void increaseSets (){
        if(sets>=20){
            Toast toastMax = Toast.makeText(getApplicationContext(), "Número máximo alcanzado", Toast.LENGTH_SHORT);
            toastMax.show();
        }else{
            sets++;
        }

        numberSets.setText(String.valueOf(sets));
    }
    public boolean validateTask(){

        name = String.valueOf(taskName.getText());
        boolean valid = true;
        if (name.length()<1){
            valid = false;
            Toast toastFalse = Toast.makeText(getApplicationContext(), "El nombre no puede quedar vacio", Toast.LENGTH_LONG);
            toastFalse.show();
        }else if (sets<1){
            valid = false;
            Toast toastFalse = Toast.makeText(getApplicationContext(), "La rutina no puede tener menos de 1 set", Toast.LENGTH_LONG);
            toastFalse.show();
        }else if (targetReps<1){
            valid = false;
            Toast toastFalse = Toast.makeText(getApplicationContext(), "La rutina no puede tener menos de 1 repeticion", Toast.LENGTH_LONG);
            toastFalse.show();
        }
        return valid;
    }

    public void callInsertNewTask(){
        Task task = new Task(name, status, sets, targetReps, currentReps, id);
        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);
        double insert = dbHelper.insertTask(task);
        Toast toast = Toast.makeText(getApplicationContext(), "Ejercicio " +count + "º añadido en la rutina.", Toast.LENGTH_LONG);
        toast.show();
        Log.i("Id nueva rutina: ", "" + insert);

        sets = 1;
        targetReps = 1;
        taskName.setText("");
        numberReps.setText("1");
        numberSets.setText("1");

    }
}
