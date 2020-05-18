package com.example.workoutlist;

public class Routine {
    private int id_Routine;
    private String name;
    private String status;
    private int numberSteps;
    private int stepsCompleted;
    private String type;

    public Routine() {
    }

    public Routine(int id_Routine, String name, String status, int numberSteps, int stepsCompleted, String type) {
        this.id_Routine = id_Routine;
        this.name = name;
        this.status = status;
        this.numberSteps = numberSteps;
        this.stepsCompleted = stepsCompleted;
        this.type = type;
    }

    public Routine(String name, String status, int numberSteps, int stepsCompleted, String type) {
        this.name = name;
        this.status = status;
        this.numberSteps = numberSteps;
        this.stepsCompleted = stepsCompleted;
        this.type = type;
    }

    public int getId_Routine() {
        return id_Routine;
    }

    public void setId_Routine(int id_Routine) {
        this.id_Routine = id_Routine;
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

    public int getNumberSteps() {
        return numberSteps;
    }

    public void setNumberSteps(int numberSteps) {
        this.numberSteps = numberSteps;
    }

    public int getStepsCompleted() {
        return stepsCompleted;
    }

    public void setStepsCompleted(int stepsCompleted) {
        this.stepsCompleted = stepsCompleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProgress(){

        double progress = 0;

        if (stepsCompleted>0){
            progress = ((double)stepsCompleted/(double)numberSteps)*100;
        }
        int percent = (int)progress;
        return percent;
    }

    public Routine rutinaTorso(){

        String name = "Rutina Torso";
        String status = "Haciendo";
        int nSteps = 5;
        int stepsComp = 0;
        String type = "fitness";

        Routine rutina = new Routine(name, status, nSteps, stepsComp, type);

        return rutina;
    }
}
