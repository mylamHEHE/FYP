package com.example.user.smartfitnesstrainer.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.example.user.smartfitnesstrainer.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class ResentEmailActivity extends AppCompatActivity {
    private Random rnd = new Random();//rnd.nextInt(90)
    private int lastXPoint = 2;
    private int lastXPoint1 = 2;
    private Handler mHandler = new Handler();
    private LineGraphSeries<DataPoint> series, series1;

    private ResentEmailActivity.MyBroadcaseReceiver1 m_MyReceiver1;
    private double sender;

    public class MyBroadcaseReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            sender = intent.getDoubleExtra("sender_name",0);
            Log.d("shb",String.valueOf(sender));

            //tommy change 78 if(sender==78)sender=0;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(m_MyReceiver1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.resentemail);

        m_MyReceiver1 = new ResentEmailActivity.MyBroadcaseReceiver1();
        IntentFilter itFilter = new IntentFilter("tw.android.MY_BROADCAST1");
        registerReceiver(m_MyReceiver1, itFilter);

        GraphView graph = (GraphView)findViewById(R.id.graph);
        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
        });
        series1 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,40),
        });
        addRandomDataPoint();
        addRandomDataPoint1();
        series1.setColor(Color.RED);

        graph.addSeries(series);
        graph.addSeries(series1);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(90);
        //graph.getViewport().setScalable(true);
    }

    private void addRandomDataPoint(){
        mHandler.postDelayed(new Runnable(){
            @Override
            public void run(){
                lastXPoint++;
                series.appendData(new DataPoint(lastXPoint,sender),false,100);
                addRandomDataPoint();
            }
        },1000);
    }
    private void addRandomDataPoint1(){
        mHandler.postDelayed(new Runnable(){
            @Override
            public void run(){
                lastXPoint1++;
                series1.appendData(new DataPoint(lastXPoint,40),false,100);
                addRandomDataPoint1();
            }
        },1000);
    }
}
