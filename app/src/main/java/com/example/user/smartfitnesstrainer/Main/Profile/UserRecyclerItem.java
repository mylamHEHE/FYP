package com.example.user.smartfitnesstrainer.Main.Profile;

public class UserRecyclerItem {
    private String head;
    private String create_date;
    public UserRecyclerItem(String head,String create_date){
        this.head = head;
        this.create_date = create_date;
    }

    public String getHead() {
        return head;
    }

    public String getCreate_date() {
        return create_date;
    }
}
