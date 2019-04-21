package com.example.user.smartfitnesstrainer.Main.Profile;

public class Detail_Player_History {

    /**
     * ref_id : 2
     * name : Cardio Chanllege
     * succuessful : true
     * round_data : [[1, 2], [5], [10, 20, 30]]
     * total_exercise : 3
     * date : 2019-4-13
     */

    private int ref_id;
    private String name;
    private String list_name;
    private boolean succuessful;
    private String round_data;
    private int total_exercise;
    private String date;

    public int getRef_id() {
        return ref_id;
    }

    public void setRef_id(int ref_id) {
        this.ref_id = ref_id;
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

    public String getRound_data() {
        return round_data;
    }

    public void setRound_data(String round_data) {
        this.round_data = round_data;
    }
    public String getList_name()
    {
        return list_name;
    }
    public void setList_name(String list_name)
    {
        this.list_name = list_name;
    }
    public int getTotal_exercise() {
        return total_exercise;
    }

    public void setTotal_exercise(int total_exercise) {
        this.total_exercise = total_exercise;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
