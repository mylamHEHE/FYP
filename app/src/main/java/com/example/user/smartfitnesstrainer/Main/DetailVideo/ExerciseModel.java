package com.example.user.smartfitnesstrainer.Main.DetailVideo;

public class ExerciseModel {
    String name;
    int bronze;
    int silver;
    int gold;
    int duration;
    int hardness;
    public ExerciseModel(String name, int bronze, int silver, int gold,int duration,int hardness) {
        this.name = name;
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
        this.duration = duration;
        this.hardness = hardness;
    }
}
