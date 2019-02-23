package com.example.user.smartfitnesstrainer.Main.Video_inner;

import com.example.user.smartfitnesstrainer.Main.exercise_selection_page.Playlist;

public class Exercise_Format {

    public Playlist item_id;
    public int current_user;

    public Playlist getItem_id() {
        return item_id;
    }

    public void setItem_id(Playlist item_id) {
        this.item_id = item_id;
    }

    public  int getCurrent_user(){
        return current_user;
    }
    public void setCurrent_user(int current_user){this.current_user = current_user;}
}
