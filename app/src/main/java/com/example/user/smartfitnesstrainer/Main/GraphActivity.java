package com.example.user.smartfitnesstrainer.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.user.smartfitnesstrainer.R;

public class GraphActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1x, series1y, series2x, series2y;

    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.graph);

        double x,y;
        x = 0;

        GraphView graph = (GraphView)findViewById(R.id.graph);
        GraphView graph1 = (GraphView)findViewById(R.id.graph1);
        series1x = new LineGraphSeries<>();
        series1y = new LineGraphSeries<>();
        series2x = new LineGraphSeries<>();
        series2y = new LineGraphSeries<>();

        int numDataPoints1 = 10;
        for(int i = 0; i < numDataPoints1; i++){
            x = x + 0.1;
            y = Math.cos(x);
            double y2 = Math.exp(x);
            series1x.appendData(new DataPoint(x,y), true, 60);
            series1y.appendData(new DataPoint(x,y2), true, 60);
        }
        series1x.setColor(Color.RED);
        series1y.setColor(Color.GREEN);
        graph.addSeries(series1x);
        graph.addSeries(series1y);

        //StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        //staticLabelsFormatter.setHorizontalLabels(new String[] {"old", "middle", "new"});
        //staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});
        //graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        int numDataPoints2 = 20;
        for(int i = 0; i < numDataPoints2; i++){
            x = x + 0.1;
            y = Math.sin(x);
            double y2 = Math.exp(x);
            series2x.appendData(new DataPoint(x,y), true, 60);
            series2y.appendData(new DataPoint(x,y2), true, 60);
        }
        series2x.setColor(Color.RED);
        series2y.setColor(Color.GREEN);
        graph1.addSeries(series2x);
        graph1.addSeries(series2y);
    }
}
