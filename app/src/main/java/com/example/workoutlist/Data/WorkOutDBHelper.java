package com.example.workoutlist.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.workoutlist.Routine;
import com.example.workoutlist.Task;

import java.net.StandardSocketOptions;
import java.util.ArrayList;

public class WorkOutDBHelper extends SQLiteOpenHelper {

    //Nombre de la base de datos
    private static final String DATABASE_NAME = "ListYL.db";

    //Version de la base de datos, actualizarla ante los cambios
    private static final int DATABASE_VERSION = 1;

    public WorkOutDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ROUTINE_TABLE = "Create table " + WorkOutContract.RoutineEntry.TABLE_NAME +
                "(" + WorkOutContract.RoutineEntry._ID + " Integer primary key autoincrement, "
                + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME + " text not null, "
                + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " text not null, "
                + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS + " Integer not null, "
                + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP + " Integer not null, "
                + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE + " text not null " + ");";

        db.execSQL(SQL_CREATE_ROUTINE_TABLE);

        SQL_CREATE_ROUTINE_TABLE = "Create table " + WorkOutContract.TaskEntry.TABLE_NAME +
                "(" + WorkOutContract.TaskEntry._ID + " Integer primary key autoincrement, "
                + WorkOutContract.TaskEntry.COLUMN_TASK_NAME + " text not null, "
                + WorkOutContract.TaskEntry.COLUMN_TASK_STATUS + " text not null, "
                + WorkOutContract.TaskEntry.COLUMN_TASK_SETS + " Integer not null, "
                + WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS + " Integer not null, "
                + WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS + " Integer not null, "
                + WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE + " Integer not null, "
                + " constraint " + WorkOutContract.TaskEntry.TABLE_NAME +"_fk foreign key ("
                + WorkOutContract.TaskEntry._ID + ") references "
                + WorkOutContract.RoutineEntry.TABLE_NAME + " (" + WorkOutContract.RoutineEntry._ID + ") on delete cascade on update cascade " + ");";

        db.execSQL(SQL_CREATE_ROUTINE_TABLE);

       insertPredefRoutineAndTask(db);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Insertar nueva rutina
    public long insertRoutine (Routine rutina){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME, rutina.getName());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS, rutina.getStatus());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS, rutina.getNumberSteps());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP, rutina.getStepsCompleted());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE, rutina.getType());

        long newRowID = db.insert(WorkOutContract.RoutineEntry.TABLE_NAME, null, contentValues);

        return newRowID;
    }

    //Insertar nueva tarea
    public long insertTask (Task tarea){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_NAME, tarea.getName());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_STATUS, tarea.getStatus());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_SETS, tarea.getSets());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS, tarea.getTargetReps());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS, tarea.getCurrentReps());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE, tarea.getCodRoutine());

        long newRowID = db.insert(WorkOutContract.TaskEntry.TABLE_NAME, null, contentValues);

        return newRowID;
    }

    //Actualizar una rutina
    public long updateRoutine (int idRutina){

        Routine rutina = displayRoutineInfo(idRutina);

        SQLiteDatabase db = this.getWritableDatabase();

        if (!rutina.getStatus().equalsIgnoreCase("Predefinida")){
            if (stepsCompletes(rutina.getId_Routine())>=rutina.getNumberSteps()){
                rutina.setStatus("Hecho");
            }else{
                rutina.setStatus("Haciendo");
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME, rutina.getName());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS, rutina.getStatus());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS, rutina.getNumberSteps());
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP, stepsCompletes(rutina.getId_Routine()));
        contentValues.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE, rutina.getType());

        String selection = WorkOutContract.RoutineEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(rutina.getId_Routine())};

        long updateRowID = db.update(WorkOutContract.RoutineEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        return updateRowID;
    }

    //Actualizar una tarea
    public long updateTask (Task tarea){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_NAME, tarea.getName());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_STATUS, tarea.getStatus());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_SETS, tarea.getSets());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS, tarea.getTargetReps());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS, tarea.getCurrentReps());
        contentValues.put(WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE, tarea.getCodRoutine());

        String selection = WorkOutContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(tarea.getId_Task())};

        long updateRowID = db.update(WorkOutContract.TaskEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        return updateRowID;
    }

    //Borrar una rutina
    public int deleteRoutine (int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Tasks", WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE+" = ?", new String[]{Integer.toString(id)});

        return db.delete("Routines", "_ID = ?", new String[]{Integer.toString(id)});
    }

    /*//Borrar una tarea
    public int deleteTask (int id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("Tasks", "ID = ?",  new String[] {Integer.toString(id)});
    }*/



    //Mostrar Rutinas no completadas
    public ArrayList<Routine> displayDataBaseInfoCurrents() {

        ArrayList<Routine> routinesInfo = new ArrayList<Routine>();
        SQLiteDatabase db = this.getReadableDatabase();

        //La proyeccion nos va a indicar los campos de la tabla que nos interesa consultar.
        String [] projection = {
                WorkOutContract.RoutineEntry._ID ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE
        };

        String selection = WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " = ?";
        String[] selectionArgs = {"Haciendo"};
        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.RoutineEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        //Obtenemos los indices de las columnas
        int idColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry._ID);
        int nameColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME);
        int statusColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS);
        int nStepsColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS);
        int stepsCompColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP);
        int typeColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE);

        //Con cada uno de los indices podemos recorrer las filas

        while (cursor.moveToNext()){
            int currentId = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            String currentStatus = cursor.getString(statusColumn);
            int currentNSteps = cursor.getInt(nStepsColumn);
            int currentStepsComp = cursor.getInt(stepsCompColumn);
            String currentType = cursor.getString(typeColumn);

            if (currentName.isEmpty() || currentStatus.isEmpty() || Integer.toString(currentNSteps).isEmpty() || Integer.toString(currentStepsComp).isEmpty() || currentType.isEmpty()){
                routinesInfo.add(null);
            }else {
                Routine currentRoutine = new Routine(currentId, currentName, currentStatus, currentNSteps, currentStepsComp, currentType);
                routinesInfo.add(currentRoutine);
            }
        }

        return  routinesInfo;
    }

    //Mostrar Rutinas ya completadas
    public ArrayList<Routine> displayDataBaseInfoCompleted() {

        ArrayList<Routine> routinesInfo = new ArrayList<Routine>();
        SQLiteDatabase db = this.getReadableDatabase();

        //La proyeccion nos va a indicar los campos de la tabla que nos interesa consultar.
        String [] projection = {
                WorkOutContract.RoutineEntry._ID ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE
        };

        String selection = WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " = ?";
        String[] selectionArgs = {"Hecho"};
        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.RoutineEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        //Obtenemos los indices de las columnas
        int idColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry._ID);
        int nameColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME);
        int statusColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS);
        int nStepsColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS);
        int stepsCompColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP);
        int typeColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE);

        //Con cada uno de los indices podemos recorrer las filas

        while (cursor.moveToNext()){
            int currentId = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            String currentStatus = cursor.getString(statusColumn);
            int currentNSteps = cursor.getInt(nStepsColumn);
            int currentStepsComp = cursor.getInt(stepsCompColumn);
            String currentType = cursor.getString(typeColumn);

            if (currentName.isEmpty() || currentStatus.isEmpty() || Integer.toString(currentNSteps).isEmpty() || Integer.toString(currentStepsComp).isEmpty() || currentType.isEmpty()){
                routinesInfo.add(null);
            }else {
                
                Routine currentRoutine = new Routine(currentId, currentName, currentStatus, currentNSteps, currentStepsComp, currentType);
                routinesInfo.add(currentRoutine);
            }
        }

        return  routinesInfo;
    }

    //Método para obtener solo la rutina con un ID en especifico
    public Routine displayRoutineInfo(int id) {

        Routine currentRoutine = new Routine();
        SQLiteDatabase db = this.getReadableDatabase();

        //La proyeccion nos va a indicar los campos de la tabla que nos interesa consultar.
        String [] projection = {
                WorkOutContract.RoutineEntry._ID ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE
        };
        String[] selectionArgs = { String.valueOf(id) };

        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.RoutineEntry.TABLE_NAME,
                projection,
                WorkOutContract.RoutineEntry._ID + "= ?",
                selectionArgs,
                null,
                null,
                null
        );

        //Obtenemos los indices de las columnas
        int idColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry._ID);
        int nameColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME);
        int statusColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS);
        int nStepsColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS);
        int stepsCompColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP);
        int typeColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE);

        //Con cada uno de los indices podemos recorrer las filas

        if (cursor != null) {
            cursor.moveToLast();
            int currentId = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            String currentStatus = cursor.getString(statusColumn);
            int currentNSteps = cursor.getInt(nStepsColumn);
            int currentStepsComp = cursor.getInt(stepsCompColumn);
            String currentType = cursor.getString(typeColumn);


            currentRoutine = new Routine(currentId, currentName, currentStatus, currentNSteps, currentStepsComp, currentType);

        }
        cursor.close();
        db.close();

        return  currentRoutine;
    }

    //Método para obtener solo las rutinas con un ID en especifico
    public Task displayTaskInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String [] projection = {
                WorkOutContract.TaskEntry._ID ,
                WorkOutContract.TaskEntry.COLUMN_TASK_NAME ,
                WorkOutContract.TaskEntry.COLUMN_TASK_STATUS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_SETS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE
        };

        String selection = WorkOutContract.TaskEntry._ID + " = ? ";
        String[] selectionArgs = { String.valueOf(id) };


        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.TaskEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        //Obtenemos los indices de las columnas
        int idColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry._ID);
        int nameColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_NAME);
        int statusColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_STATUS);
        int setsColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_SETS);
        int targetColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS);
        int currentRepsColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS);
        int idRoutineColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE);

        Task currentTask = new Task();

        if (cursor != null) {
            cursor.moveToLast();
            //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
            int currentId = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            String currentStatus = cursor.getString(statusColumn);
            int currentSets = cursor.getInt(setsColumn);
            int currentTarget = cursor.getInt(targetColumn);
            int currentReps = cursor.getInt(currentRepsColumn);
            int currentIdRoutine = cursor.getInt(idRoutineColumn);

            currentTask = new Task(currentId, currentName, currentStatus, currentSets, currentTarget, currentReps, currentIdRoutine);
        }

        //Cerramos el cursor y la conexion con la base de datos
        cursor.close();
        db.close();

        return currentTask;
    }

    //Método para obtener los ejercicios de una rutina determinada
    public ArrayList<Task> displayWorkOutRoutine (int id){

        ArrayList<Task> workOutRoutine = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();

        //La proyeccion nos va a indicar los campos de la tabla que nos interesa consultar.
        String [] projection = {
                WorkOutContract.TaskEntry._ID ,
                WorkOutContract.TaskEntry.COLUMN_TASK_NAME ,
                WorkOutContract.TaskEntry.COLUMN_TASK_STATUS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_SETS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS ,
                WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE
        };
        String[] selectionArgs = { String.valueOf(id) };

        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.TaskEntry.TABLE_NAME,
                projection,
                WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE + "= ?",
                selectionArgs,
                null,
                null,
                null
        );

        //Obtenemos los indices de las columnas
        int idColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry._ID);
        int nameColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_NAME);
        int statusColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_STATUS);
        int setsColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_SETS);
        int targetColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS);
        int currentRepsColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS);
        int idRoutineColumn = cursor.getColumnIndex(WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE);

        //Con cada uno de los indices podemos recorrer las filas

        while (cursor.moveToNext()){
            int currentId = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            String currentStatus = cursor.getString(statusColumn);
            int currentSets = cursor.getInt(setsColumn);
            int currentTarget = cursor.getInt(targetColumn);
            int currentReps = cursor.getInt(currentRepsColumn);
            int currentIdRoutine = cursor.getInt(idRoutineColumn);

            if (currentName.isEmpty() || currentStatus.isEmpty() || Integer.toString(currentSets).isEmpty() || Integer.toString(currentTarget).isEmpty() || Integer.toString(currentReps).isEmpty() || Integer.toString(currentIdRoutine).isEmpty()){
                workOutRoutine.add(null);
            }else {

                Task currentTask = new Task(currentId, currentName, currentStatus, currentSets, currentTarget, currentReps, currentIdRoutine);
                workOutRoutine.add(currentTask);
            }
        }

        return  workOutRoutine;
    }

    //Obtiene el ultimo id para añadirle los ejercicios.
    public Integer[] lastIdSteps(){

        SQLiteDatabase db = this.getReadableDatabase();

        String [] projection = {
                WorkOutContract.RoutineEntry._ID ,
        };
        String orderBy = WorkOutContract.RoutineEntry._ID;

        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.RoutineEntry.TABLE_NAME,
                null,
                null,
                null,
               null,
                null,
                orderBy
        );

        int id = 0;
        int steps = 0;

        if (cursor != null) {
            cursor.moveToLast();
            //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
            id = cursor.getInt(cursor.getColumnIndex(WorkOutContract.RoutineEntry._ID));
            steps = cursor.getInt(cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS));
        }
        Integer[] info = {id,steps};
        //Cerramos el cursor y la conexion con la base de datos
        cursor.close();
        db.close();

        return info;
    }

    public int stepsCompletes(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        String sentencia = "SELECT COUNT (*) from Tasks where " + WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE + " = " + id + " and " + WorkOutContract.TaskEntry.COLUMN_TASK_STATUS + " like 'Hecho'";
        Cursor mCount = db.rawQuery(sentencia, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        return count;

    }

    public int routinesCompleted(){

        SQLiteDatabase db = this.getReadableDatabase();

        String sentencia = "SELECT COUNT (*) from routines where " + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " like 'Hecho'";
        Cursor mCount = db.rawQuery(sentencia, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        return count;

    }

    public int routinesOnGoing(){

        SQLiteDatabase db = this.getReadableDatabase();

        String sentencia = "SELECT COUNT (*) from routines where " + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " like 'Haciendo'";
        Cursor mCount = db.rawQuery(sentencia, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        return count;

    }

    public int routinesPredef(){

        SQLiteDatabase db = this.getReadableDatabase();

        String sentencia = "SELECT COUNT (*) from routines where " + WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " like 'Predefinida'";
        Cursor mCount = db.rawQuery(sentencia, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        return count;

    }

    public ArrayList<Routine> displayDataBaseInfoPredef() {

        ArrayList<Routine> routinesInfo = new ArrayList<Routine>();
        SQLiteDatabase db = this.getReadableDatabase();

        //La proyeccion nos va a indicar los campos de la tabla que nos interesa consultar.
        String [] projection = {
                WorkOutContract.RoutineEntry._ID ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP ,
                WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE
        };

        String selection = WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS + " = ?";
        
        String[] selectionArgs = {"Predefinida"};
        
        @SuppressLint("Recycle") Cursor cursor = db.query(
                WorkOutContract.RoutineEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        //Obtenemos los indices de las columnas
        int idColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry._ID);
        int nameColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME);
        int statusColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS);
        int nStepsColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS);
        int stepsCompColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP);
        int typeColumn = cursor.getColumnIndex(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE);

        //Con cada uno de los indices podemos recorrer las filas

        while (cursor.moveToNext()){
            int currentId = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            String currentStatus = cursor.getString(statusColumn);
            int currentNSteps = cursor.getInt(nStepsColumn);
            int currentStepsComp = cursor.getInt(stepsCompColumn);
            String currentType = cursor.getString(typeColumn);

            if (currentName.isEmpty() || currentStatus.isEmpty() || Integer.toString(currentNSteps).isEmpty() || Integer.toString(currentStepsComp).isEmpty() || currentType.isEmpty()){
                routinesInfo.add(null);
            }else {
                Routine currentRoutine = new Routine(currentId, currentName, currentStatus, currentNSteps, currentStepsComp, currentType);
                routinesInfo.add(currentRoutine);
            }
        }

        return  routinesInfo;
    }

    public void insertPredefRoutineAndTask(SQLiteDatabase db){
        //Inserta una rutina predefinida

        String name = "Rutina Torso";
        String status = "Predefinida";
        int nSteps = 5;
        int stepsComp = 0;
        String type = "fitness";

        ContentValues contentValuesR = new ContentValues();
        contentValuesR.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NAME, name);
        contentValuesR.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STATUS, status);
        contentValuesR.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_NSTEPS, nSteps);
        contentValuesR.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_STEPSCOMP, stepsComp);
        contentValuesR.put(WorkOutContract.RoutineEntry.COLUMN_ROUTINE_TYPE, type);

        db.insert(WorkOutContract.RoutineEntry.TABLE_NAME, null, contentValuesR);

        //Inserta los ejercicios de la rutina predefinida

        String[] nameT = {"Dominadas", "Flexiones", "Extension Triceps", "Curl de Biceps", "Remo al menton"};
        String statusT = "Haciendo";
        int[] sets = {3, 3, 2, 2, 3};
        int[] targetReps = {15, 20, 20, 15, 15};
        int currentReps = 0;
        int codRoutine = 1;



        for ( int i = 0; i < nameT.length ; i++ ){

            ContentValues contentValuesT = new ContentValues();
            contentValuesT.put(WorkOutContract.TaskEntry.COLUMN_TASK_NAME, nameT[i]);
            contentValuesT.put(WorkOutContract.TaskEntry.COLUMN_TASK_STATUS, statusT);
            contentValuesT.put(WorkOutContract.TaskEntry.COLUMN_TASK_SETS, sets[i]);
            contentValuesT.put(WorkOutContract.TaskEntry.COLUMN_TASK_TARGET_REPS, targetReps[i]);
            contentValuesT.put(WorkOutContract.TaskEntry.COLUMN_TASK_CURRENT_REPS, currentReps);
            contentValuesT.put(WorkOutContract.TaskEntry.COLUMN_TASK_ID_ROUTINE, codRoutine);

            db.insert(WorkOutContract.TaskEntry.TABLE_NAME, null, contentValuesT);
        }


    }
}
