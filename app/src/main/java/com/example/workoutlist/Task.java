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

    public Task ejer1(){

        String name = "dominadas";
        String status = "Haciendo";
        int sets = 3;
        int targetReps = 15;
        int currentReps = 0;
        int codRoutine = 1;

        Task dominadas = new Task(name, status, sets, targetReps, currentReps, codRoutine);
        return dominadas;
    }
    public Task ejer2(){

        String name = "flexiones";
        String status = "Haciendo";
        int sets = 3;
        int targetReps = 20;
        int currentReps = 0;
        int codRoutine = 1;

        Task flexiones = new Task(name, status, sets, targetReps, currentReps, codRoutine);
        return flexiones;
    }
    public Task ejer3(){

        String name = "Extension Triceps";
        String status = "Haciendo";
        int sets = 2;
        int targetReps = 20;
        int currentReps = 0;
        int codRoutine = 1;

        Task triceps = new Task(name, status, sets, targetReps, currentReps, codRoutine);
        return triceps;
    }
    public Task ejer4(){

        String name = "Curl Biceps";
        String status = "Haciendo";
        int sets = 3;
        int targetReps = 15;
        int currentReps = 0;
        int codRoutine = 1;

        Task biceps = new Task(name, status, sets, targetReps, currentReps, codRoutine);
        return biceps;
    }
    public Task ejer5(){

        String name = "Remo menton";
        String status = "Haciendo";
        int sets = 3;
        int targetReps = 12;
        int currentReps = 0;
        int codRoutine = 1;

        Task hombro = new Task(name, status, sets, targetReps, currentReps, codRoutine);
        return hombro;
    }

    public ArrayList<Task> workOut(){
        ArrayList<Task> rutinaTorso = new ArrayList<Task>();
        rutinaTorso.add(ejer1());
        rutinaTorso.add(ejer2());
        rutinaTorso.add(ejer3());
        rutinaTorso.add(ejer4());
        rutinaTorso.add(ejer5());
        return rutinaTorso;

    }
}
