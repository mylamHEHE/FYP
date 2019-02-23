package com.example.user.smartfitnesstrainer.Main.Video_inner;

public class Exercise_Format {


    /**
     * id : 2
     * name : Plank
     * difficulty : 5
     * thumbnail : null
     */

    private int id;
    private String name;
    private int difficulty;
    private Object thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }
}
