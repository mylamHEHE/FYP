package com.example.user.smartfitnesstrainer.Main.exercise_selection_page;

public class Playlist {

    /**
     * agegroup : All age
     * cover_photo : https://cdn.liftbrands.com/snap/uploads/location_tour/2018/may/30/Urban-Fitness-Club-Battle-Ropes_ori.jpg
     * difficulty : Easy
     * equipment : Sensor x1
     * name : Cardio
     */

    private String agegroup;
    private String cover_photo;
    private String difficulty;
    private String equipment;
    private String name;
    private String description;

    public String getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String description) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setgetDescription(String description) {
        this.description = description;
    }
}
