package com.example.user.smartfitnesstrainer.Main.Profile;

public class Player_history {

    /**
     * name : Cardio Exercise
     * succuessful : true
     * date : 2011-11-3
     */

    private String name;
    private boolean succuessful;
    private String date;
    private String historyID;

    public String getHistoryID()
    {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuccuessful() {
        return succuessful;
    }

    public void setSuccuessful(boolean succuessful) {
        this.succuessful = succuessful;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
