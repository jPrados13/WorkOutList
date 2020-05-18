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
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutlist.Data.WorkOutDBHelper;

public class UpdateTask extends AppCompatActivity {

    //View variable
    EditText taskName;
    TextView numberReps;
    ImageButton plus;
    ImageButton less;
    Button update;


    //Task variables
    int idTask = 0;
    String name = "";
    String status = "Haciendo";
    int sets = 1;
    int targetReps = 1;
    int currentReps = 0;
    int idRoutine = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        Intent intent = getIntent();
        idTask = intent.getIntExtra("IdTask", 0);

        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Task task = dbHelper.displayTaskInfo(idTask);
        name = task.getName();
        status = task.getStatus();
        sets = task.getSets();
        targetReps = task.getTargetReps();
        currentReps = task.getCurrentReps();
        idRoutine = task.getCodRoutine();


        taskName = findViewById(R.id.editTextTaskUpdateName);
        numberReps = findViewById(R.id.numberRepsTaskUpdate);
        plus = findViewById(R.id.buttonPlusRepsUpd);
        less = findViewById(R.id.buttonLessRepsUpd);
        update = findViewById(R.id.buttonUpdTask);

        taskName.setText(name);
        numberReps.setText(String.valueOf(currentReps));

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActions(v);
            }
        };

        plus.setOnClickListener(listener);
        less.setOnClickListener(listener);
        update.setOnClickListener(listener);

    }

    public void onClickActions (View view){

        switch (view.getId()){

            case R.id.buttonPlusRepsUpd:
                increase();
                break;
            case R.id.buttonLessRepsUpd:
                decrease();
                break;
            case  R.id.buttonUpdTask:
                if (validateTask()){
                    callUpdateTask();
                    Intent i = new Intent(getApplicationContext(), WorkOutRoutine.class);
                    i.putExtra("IdRoutine", idRoutine);
                    startActivity(i);
                    finish();
                }
                break;

        }
    }

    public void decrease (){
        if(currentReps<=0){
            Toast toastMin = Toast.makeText(getApplicationContext(), "La rutina no puede tener menos de 1 ejercicio", Toast.LENGTH_SHORT);
            toastMin.show();
        }else{
            currentReps--;
        }
        numberReps.setText(String.valueOf(currentReps));
    }

    public void increase (){
        if(currentReps>=99){
            Toast toastMax = Toast.makeText(getApplicationContext(), "Número máximo alcanzado", Toast.LENGTH_SHORT);
            toastMax.show();
        }else{
            currentReps++;
        }

        numberReps.setText(String.valueOf(currentReps));
    }

    public boolean validateTask(){

        name = String.valueOf(taskName.getText());
        boolean valid = true;
        if (name.length()<1){
            valid = false;
            Toast toastFalse = Toast.makeText(getApplicationContext(), "El nombre no puede quedar vacio", Toast.LENGTH_LONG);
            toastFalse.show();
        }

        return valid;
    }

    public void callUpdateTask(){

        WorkOutDBHelper dbHelper = new WorkOutDBHelper(this);

        if (currentReps>=targetReps){
            if (status.equalsIgnoreCase("Haciendo")){
                status = "Hecho";
            }

        }else{
            if (status.equalsIgnoreCase("Hecho")){
                status = "Haciendo";
            }
        }
        Task task = new Task(idTask, name, status, sets, targetReps, currentReps, idRoutine);
        dbHelper.updateTask(task);
        dbHelper.updateRoutine(idRoutine);
        Toast toast = Toast.makeText(getApplicationContext(), "Ejercicio actualizado.", Toast.LENGTH_LONG);
        toast.show();
    }
}
