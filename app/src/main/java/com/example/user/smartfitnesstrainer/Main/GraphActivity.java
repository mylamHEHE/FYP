package com.example.user.smartfitnesstrainer.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.Profile.Detail_Player_History;
import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.user.smartfitnesstrainer.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;

public class GraphActivity extends AppCompatActivity {

    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_Base);
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    PrefKey prefKey;
    private LineGraphSeries<DataPoint> series1x, series1y, series2x, series2y, series3x, series3y;
    public double addORminus(double pre, double cur){
        if(cur == pre){
            return cur;
        }
        else if(pre>cur){
            return (pre - cur)*(-1);
        }else {
            return cur - pre;
        }

    }
    private void fetchResult(final int exercise_key){
        Call<Detail_Player_History> call = userClient.getRecord("application/x-www-form-urlencoded","Bearer "+prefKey.getAccess_token(),exercise_key);
        call.enqueue(new Callback<Detail_Player_History>() {
            @Override
            public void onResponse(Call<Detail_Player_History> call, Response<Detail_Player_History> response) {
                if (response.isSuccessful()) {
                    try {



                        JSONArray round = new JSONArray(response.body().getRound_data());
                        for(int rot=0;rot<round.length();rot++)
                        {
                            Log.d("yea"+String.valueOf(exercise_key),String.valueOf(rot));
                            JSONObject each = new JSONObject(round.get(rot).toString());
/*
                            ArrayList<Integer> graphplot = new ArrayList<>();
                            for (int point=0;point<each.length();point++)
                            {
                                graphplot.add(each.getInt(point));
                            }
                            createGraph(graphplot);
                            */
                        }

                        //tomilia: get Profile stats

                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    //  Toast.makeText(getActivity(),response.code(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Detail_Player_History> call, Throwable t) {
               Log.d("psdv","fai;");
            }
        });
    }
    private void createGraph(ArrayList<Integer> graph)
    {
        Log.d("tagx",graph.toArray().toString());
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
       // unregisterReceiver(m_MyReceiver1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.graph);
        prefKey = new PrefKey(getApplicationContext());
        if (getIntent().hasExtra("exkey")) {
            int x = getIntent().getIntExtra("exkey",0);
            Log.d("xcz",String.valueOf(x));
            fetchResult(x);
        } else {
            finish();
        }

        //receive broadcast
        /*
        m_MyReceiver1 = new MyBroadcaseReceiver1();
        IntentFilter itFilter = new IntentFilter("tw.android.MY_BROADCAST1");
        registerReceiver(m_MyReceiver1, itFilter);
*/
        int[] testcase ={20 , 30 , 25, 50, 45, 50, 60, 55, 45 , 9};

        double x1 = 0, y1 = 0;
        double x2 = 0, y2 = 5;
        double x3 = 0, y3 = 5;

        GraphView graph1 = (GraphView)findViewById(R.id.graph1);
        GraphView graph2 = (GraphView)findViewById(R.id.graph2);
        GraphView graph3 = (GraphView)findViewById(R.id.graph3);
        series1x = new LineGraphSeries<>();
        series1y = new LineGraphSeries<>();
        series2x = new LineGraphSeries<>();
        series2y = new LineGraphSeries<>();
        series3x = new LineGraphSeries<>();
        series3y = new LineGraphSeries<>();

        int numDataPoints1 = 90;
        for(int i = 0; i < 9; i++){
            x1 = x1 + 0.1;
            y1 = testcase[i] + addORminus(testcase[i],testcase[i+1]);
            series1x.appendData(new DataPoint(x1,45), true, 60);
            series1y.appendData(new DataPoint(x1,y1), true, 60);
        }

        series1x.setColor(Color.RED);
        series1y.setColor(Color.GREEN);
        graph1.addSeries(series1x);
        graph1.addSeries(series1y);

        int numDataPoints2 = 90;
        for(int i = 0; i < numDataPoints2; i++){
            x2 = x2 + 0.1;
            y2 = addORminus(0,1);
            series2x.appendData(new DataPoint(x2,0), true, 60);
            series2y.appendData(new DataPoint(x2,3), true, 60);
        }
        series2x.setColor(Color.RED);
        series2y.setColor(Color.GREEN);
        graph2.addSeries(series2x);
        graph2.addSeries(series2y);

        int numDataPoints3 = 90;
        for(int i = 0; i < numDataPoints3; i++){
            x3 = x3 + 0.1;
            y3 = addORminus(3,3);
            series3x.appendData(new DataPoint(x3,25), true, 60);
            series3y.appendData(new DataPoint(x3,4), true, 60);
        }
        series3x.setColor(Color.RED);
        series3y.setColor(Color.GREEN);
        graph3.addSeries(series3x);
        graph3.addSeries(series3y);
    }

}
