package com.example.workoutlist.Data;

import android.provider.BaseColumns;

public class WorkOutContract {
    private WorkOutContract(){

    }

    public static final class RoutineEntry implements BaseColumns {

        public final static String TABLE_NAME = "Routines";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ROUTINE_NAME = "Nombre";
        public final static String COLUMN_ROUTINE_STATUS = "Estado";
        public final static String COLUMN_ROUTINE_NSTEPS = "NumPasos";
        public final static String COLUMN_ROUTINE_STEPSCOMP = "PasosComp";
        public final static String COLUMN_ROUTINE_TYPE = "Tipo";

    }

    public static final class TaskEntry implements BaseColumns {

        public final static String TABLE_NAME = "Tasks";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TASK_NAME = "Nombre";
        public final static String COLUMN_TASK_STATUS = "Estado";
        public final static String COLUMN_TASK_SETS = "Sets";
        public final static String COLUMN_TASK_TARGET_REPS = "Objetivo";
        public final static String COLUMN_TASK_CURRENT_REPS = "Actual";
        public final static String COLUMN_TASK_ID_ROUTINE = "Id_Rutina";

    }
}
