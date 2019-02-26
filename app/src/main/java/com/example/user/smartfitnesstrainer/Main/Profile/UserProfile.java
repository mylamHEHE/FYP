package com.example.user.smartfitnesstrainer.Main.Profile;

import java.util.List;

public class UserProfile {

    /**
     * current : tomilia
     * first_name : tommy
     * last_name : lee
     * player_history : [{"name":"Cardio Exercise","succuessful":true,"date":"2011-11-3"}]
     */

    private String current;
    private String first_name;
    private String last_name;
    private List<PlayerHistory> player_history;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<PlayerHistory> getPlayer_history() {
        return player_history;
    }

    public void setPlayer_history(List<PlayerHistory> player_history) {
        this.player_history = player_history;
    }
    /*
        Player history
     */
    public static class PlayerHistory {
        /**
         * name : Cardio Exercise
         * succuessful : true
         * date : 2011-11-3
         */

        private String name;
        private boolean succuessful;
        private String date;

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
}
