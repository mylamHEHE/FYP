package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.util.Log;

public class Analyticzer {
    private int correctCount=0;
    public boolean analyze(int stage,double currentangle)
    {
        Log.d("analyzer",String.valueOf(stage+" "+currentangle));
        switch (stage)
        {

            case 0:
            {

                if (currentangle >= 10 && currentangle <= 40)
                {
                    correctCount++;
                    Log.d("correctLeg",String.valueOf(correctCount));
                    return true;
                }
                else
                {
                    Log.d("wrongLeg",String.valueOf(correctCount));
                    return false;
                }
            }
            case 1:
            {
                if (currentangle <=10 || currentangle >=70)
                {
                    correctCount++;
                    Log.d("correctLeg",String.valueOf(correctCount));
                    return true;
                }
                else
                {
                    Log.d("wrongLeg",String.valueOf(correctCount));
                    return false;
                }
            }
            case 2:
            {
                if (currentangle >= 18 && currentangle <= 40)
                {
                    correctCount++;
                    Log.d("correctLeg",String.valueOf(correctCount));
                    return true;
                }
                else
                {
                    Log.d("wrongLeg",String.valueOf(correctCount));
                    return false;
                }
            }
        }
        return false;
    }
}
