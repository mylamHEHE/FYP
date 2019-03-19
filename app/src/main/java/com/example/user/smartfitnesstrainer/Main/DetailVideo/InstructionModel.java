package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;

public class InstructionModel {
    String instructionUrl;

    public InstructionModel(String instructionUrl) {
        this.instructionUrl = URL_Base+"static/"+instructionUrl+".mp4";
    }
}
