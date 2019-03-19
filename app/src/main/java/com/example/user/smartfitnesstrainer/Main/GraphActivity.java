package com.example.user.smartfitnesstrainer.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.user.smartfitnesstrainer.R;

public class GraphActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1, series2;

    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.graph);

        double x,y;
        x = 0;

        GraphView graph = (GraphView)findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();

        int numDataPoints = 250;
        for(int i = 0; i < numDataPoints; i++){
            x = x + 0.1;
            y = Math.sin(x);
            double y2 = Math.exp(x);
            series1.appendData(new DataPoint(x,y), true, 60);
            series2.appendData(new DataPoint(x,y2), true, 60);
        }
        series1.setColor(Color.RED);
        series2.setColor(Color.BLUE);
        graph.addSeries(series1);
        graph.addSeries(series2);
    }
}
