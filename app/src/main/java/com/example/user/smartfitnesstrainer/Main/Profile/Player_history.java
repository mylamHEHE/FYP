package com.example.user.smartfitnesstrainer.Main.Profile;

public class Player_history {
    private String[] roll_comment;

    private String[] pitch_comment;

    private Detail_Player_History explist;

    public String[] getRoll_comment ()
    {
        return roll_comment;
    }

    public void setRoll_comment (String[] roll_comment)
    {
        this.roll_comment = roll_comment;
    }

    public String[] getPitch_comment ()
    {
        return pitch_comment;
    }

    public void setPitch_comment (String[] pitch_comment)
    {
        this.pitch_comment = pitch_comment;
    }

    public Detail_Player_History getExplist ()
    {
        return explist;
    }

    public void setExplist (Detail_Player_History explist)
    {
        this.explist = explist;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [roll_comment = "+roll_comment+", pitch_comment = "+pitch_comment+", explist = "+explist+"]";
    }
}
