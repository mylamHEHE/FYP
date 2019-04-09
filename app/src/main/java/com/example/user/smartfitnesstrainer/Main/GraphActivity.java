package com.example.user.smartfitnesstrainer.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.user.smartfitnesstrainer.R;

public class GraphActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1x, series1y, series2x, series2y, series3x, series3y;
    private MyBroadcaseReceiver1 m_MyReceiver1;
    private double sender;

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
        setContentView(R.layout.graph);
        //receive broadcast
        m_MyReceiver1 = new MyBroadcaseReceiver1();
        IntentFilter itFilter = new IntentFilter("tw.android.MY_BROADCAST1");
        registerReceiver(m_MyReceiver1, itFilter);

        int[] testcase ={20 , 30 , 25, 50, 45, 50, 60, 55, 45 , 9};

        double x1 = 0, y1 = 0;
        double x2 = 0, y2 = sender;
        double x3 = 0, y3 = sender;

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
            y2 = addORminus(sender,sender);
            series2x.appendData(new DataPoint(x2,0), true, 60);
            series2y.appendData(new DataPoint(x2,sender), true, 60);
        }
        series2x.setColor(Color.RED);
        series2y.setColor(Color.GREEN);
        graph2.addSeries(series2x);
        graph2.addSeries(series2y);

        int numDataPoints3 = 90;
        for(int i = 0; i < numDataPoints3; i++){
            x3 = x3 + 0.1;
            y3 = addORminus(sender,sender);
            series3x.appendData(new DataPoint(x3,25), true, 60);
            series3y.appendData(new DataPoint(x3,sender), true, 60);
        }
        series3x.setColor(Color.RED);
        series3y.setColor(Color.GREEN);
        graph3.addSeries(series3x);
        graph3.addSeries(series3y);
    }

}
