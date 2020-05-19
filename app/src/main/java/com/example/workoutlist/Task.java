package com.example.workoutlist;

import java.util.ArrayList;

public class Task {

    private int id_Task;
    private String name;
    private String status;
    private int sets;
    private int targetReps;
    private int currentReps;
    private int codRoutine;

    public Task() {
    }

    public Task(String name, String status, int sets, int targetReps, int currentReps, int codRoutine) {
        this.name = name;
        this.status = status;
        this.sets = sets;
        this.targetReps = targetReps;
        this.currentReps = currentReps;
        this.codRoutine = codRoutine;
    }

    public Task(int id_Task, String name, String status, int sets, int targetReps, int currentReps, int codRoutine) {
        this.id_Task = id_Task;
        this.name = name;
        this.status = status;
        this.sets = sets;
        this.targetReps = targetReps;
        this.currentReps = currentReps;
        this.codRoutine = codRoutine;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getId_Task() {
        return id_Task;
    }

    public void setId_Task(int id_Task) {
        this.id_Task = id_Task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(int targetReps) {
        this.targetReps = targetReps;
    }

    public int getCurrentReps() {
        return currentReps;
    }

    public void setCurrentReps(int currentReps) {
        this.currentReps = currentReps;
    }

    public int getCodRoutine() {
        return codRoutine;
    }

    public void setCodRoutine(int codRoutine) {
        this.codRoutine = codRoutine;
    }

    public int getProgressTask(){
        double progress = 0;

        if (currentReps>0){
            progress = ((double) currentReps/(double)targetReps)*100;
        }
        int percent = (int)progress;
        return percent;
    }


}
