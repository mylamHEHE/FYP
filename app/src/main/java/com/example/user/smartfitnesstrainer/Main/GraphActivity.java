package com.example.user.smartfitnesstrainer.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.BLE.RegistrationActivity;
import com.example.user.smartfitnesstrainer.Main.Profile.Detail_Player_History;
import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.user.smartfitnesstrainer.R;
import com.jjoe64.graphview.series.PointsGraphSeries;

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
    private TextView name_text;
    private TextView date_text;
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_Base);
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    PrefKey prefKey;
    int name_counter=0;
    int current_render_graph=0;
    private LineGraphSeries<DataPoint> series1x, series1y, series2x, series2y, series3x, series3y;
    private PointsGraphSeries dot1x;
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

                        //convert integer point to each graph

                        JSONArray round = new JSONArray(response.body().getRound_data());
                        String name=response.body().getName();
                        String date=response.body().getDate();
                        for(int rot=0;rot<round.length();rot++)
                        {
                            Log.d("yeaz"+String.valueOf(exercise_key),String.valueOf(response.body().getRef_id()));

                            Log.d("yea"+String.valueOf(exercise_key),String.valueOf(rot));
                            JSONArray each = new JSONArray(round.get(rot).toString());

                            JSONArray name_list = new JSONArray(response.body().getList_name());
                            ArrayList<Integer> graphplot = new ArrayList<>();
                            ArrayList<String> list_name = new ArrayList<>();
                            for(int a=0;a<name_list.length();a++)
                            {
                                list_name.add(name_list.getString(a));
                            }
                            for (int point=0;point<each.length();point++)
                            {
                                graphplot.add(each.getInt(point));
                            }
                            //tomilia: add to name_list
                            name_text.setText(name);
                            date_text.setText(date);
                            createGraph(graphplot,list_name);

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
    private void createGraph(ArrayList<Integer> graph,ArrayList<String> list_name)
    {
        int[] ret = new int[graph.size()];
        String[] name = new String[list_name.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = graph.get(i).intValue();
        }
        for(int i=0;i<name.length;i++)
        {
            name[i]=list_name.get(i);
        }
        onGraphCreate(ret,name);

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
        name_text = findViewById(R.id.name_text);
        date_text = findViewById(R.id.date_text);
        prefKey = new PrefKey(getApplicationContext());
        if (getIntent().hasExtra("exkey")) {
            int x = getIntent().getIntExtra("exkey",0);
            Log.d("xcz",String.valueOf(x));
            fetchResult(x);
        } else {
            finish();
        }
        Button button = (Button) findViewById(R.id.Return);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //receive broadcast
        /*
        m_MyReceiver1 = new MyBroadcaseReceiver1();
        IntentFilter itFilter = new IntentFilter("tw.android.MY_BROADCAST1");
        registerReceiver(m_MyReceiver1, itFilter);
*/
        /*
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
        */
    }
    private void onGraphCreate(int[] graph,String[] name){


        double x1 = 0, y1 = 0;
        double x2 = 0, y2 = 5;
        double x3 = 0, y3 = 5;

        //TextView tv1 = findViewById(R.id.textView1);
       // tv1.setText("vksdovks");

        TextView tv3=new TextView(getApplicationContext());
        tv3.setText(name[name_counter]);
        tv3.setTextSize(20f);

        tv3.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        for(int y:graph)
        {
            Log.d("grapx",String.valueOf(y));
        }
        GraphView graph1 = new GraphView(getApplicationContext());

       // GraphView graph2 = (GraphView)findViewById(R.id.graph2);
        //GraphView graph3 = (GraphView)findViewById(R.id.graph3);
        series1x = new LineGraphSeries<>();
        series1y = new LineGraphSeries<>();
        dot1x = new PointsGraphSeries<>();
        //series2x = new LineGraphSeries<>();
        //series2y = new LineGraphSeries<>();
        //series3x = new LineGraphSeries<>();
        //series3y = new LineGraphSeries<>();

        for(int i = 0; i < graph.length; i++){

            y1 = graph[i];
            dot1x.appendData(new DataPoint(x1,y1), true, 60);
            series1x.appendData(new DataPoint(x1,45), true, 60);
            series1y.appendData(new DataPoint(x1,y1), true, 60);
            x1 = x1 + 1;
        }

        dot1x.setShape(PointsGraphSeries.Shape.POINT);
        dot1x.setColor(Color.BLUE);
        dot1x.setSize(15f);

        graph1.getViewport().setXAxisBoundsManual(true);

        graph1.getViewport().setMinX(0);
        graph1.getViewport().setMaxX(graph.length);
        graph1.getViewport().setScrollable(true);
        series1x.setColor(Color.RED);
        series1y.setColor(Color.GREEN);
        graph1.addSeries(series1x);
        graph1.addSeries(series1y);
        graph1.addSeries(dot1x);

        graph1.invalidate();

        graph1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.addView(tv3);
        layout.addView(graph1);

        name_counter++;
        /*
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
        */
    }

}
