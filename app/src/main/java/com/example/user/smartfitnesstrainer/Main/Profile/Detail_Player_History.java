package com.example.user.smartfitnesstrainer.Main.Profile;

import android.util.Log;

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
    private String first_round_data;
    private String second_round_data;
    private int total_exercise;
    private String date;
    /**
     * pitch_comment : ['Maybe you need to keep ur legs in position and keep two legs balance!', 'Keep working! Although the balancing of back and legs also consistency is not so satisfied.', 'Keep working! Although the elevation of legs and left hand has to be straight to the ground;Consistency is not so satisfied.', 'Keep working! Although the balancing and consistency is not so satisfied.', 'Keep working! Although the balancing of back and legs also consistency is not so satisfied.', 'Keep working! Although the elevation of legs and left hand has to be straight to the ground;Consistency is not so satisfied.']
     * roll_comment : ['Very good! The rotation of the body is balanced despite consistency needs improve!', 'Excellent! The rotation of the body is very balanced!', 'Maybe you hold your hand towards the eyesight and keep two legs balance and tight!', 'Very good! The rotation of the body is balanced despite consistency needs improve!', 'Excellent! The rotation of the body is very balanced!', 'Maybe you hold your hand towards the eyesight and keep two legs balance and tight!']
     */

    private String[] pitch_comment;
    private String[] roll_comment;

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

    public String getFirst_round_data() {
        return first_round_data;
    }

    public void setFirst_round_data(String round_data) {
        this.first_round_data = round_data;
    }

    public String getSecond_round_data() {
        return second_round_data;
    }

    public void setSecond_round_data(String second_round_data) {
        this.second_round_data = second_round_data;
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

    public String[] getPitch_comment() {

        return pitch_comment;
    }

    public void setPitch_comment(String[] pitch_comment) {
        this.pitch_comment = pitch_comment;
    }

    public String[] getRoll_comment() {
        return roll_comment;
    }

    public void setRoll_comment(String[] roll_comment) {
        this.roll_comment = roll_comment;
    }
}
