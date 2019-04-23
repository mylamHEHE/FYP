package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user.smartfitnesstrainer.Main.GraphActivity;
import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.example.user.smartfitnesstrainer.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tomer.fadingtextview.FadingTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;
import static com.google.android.exoplayer2.Player.STATE_ENDED;


public class ExerciseActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_Base);
    PrefKey prefKey;
    Bundle sis;
    Thread mGraphTimer;
    String name_of_exercise;
    ExerciseListAdapter ela;
    Retrofit retrofit = builder.build();
    boolean first_in_data = false;
    private ArrayList<String> list_of_exer_name = new ArrayList<>();
    UserClient userClient = retrofit.create(UserClient.class);
    private ArrayList<String> temp = new ArrayList<>();
    private boolean isVideoCreate = false;
    private Analyticzer analyticzer = new Analyticzer();
    private FadingTextView ftv;
    private ArrayList<InstructionModel> instruction = new ArrayList<>();
    private ArrayList<VideoModel> vm = new ArrayList<>();
    private ImageButton pause;
    ArrayList<ArrayList<Integer[]>> round_list = new ArrayList<>();
    ArrayList<ArrayList<Integer[]>> second_round_list = new ArrayList<>();
    private DeviceAlert devicealert;
    private TextView mTextField;
    private int isTutorMode = 0;
    private ArrayList<ExerciseModel> exerciseModelArrayList = new ArrayList<>();
    int currentExercise = 0;
    private ProgressBar pb;
    private int stageScore = 0;
    private TextView currentScore;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageButton play;
    private int num_of_exercise = 0;
    private ArrayList<Integer> totalRound = new ArrayList<>();
    private RelativeLayout rl;
    private RelativeLayout rl0;
    private LinearLayout scoreBoard;
    private Button skipTutor;
    private boolean onFinishRepeat = false;
    private TimerTask doAsynchronousTask;
    private MediaPlayer mp;
    private TextView angle;
    private double currentreading = 0.0;
    private Handler mHandler = new Handler();
    private int lastXPoint = 2, lastXPoint1 = 2, lastXPoint2 = 2;
    private LineGraphSeries<DataPoint> series0, series1, series2, series3, series4, series5;
    private double graph_pt_pitch;
    private double graph_pt_roll;
    private double second_graph_pt_pitch;
    private double second_graph_pt_roll;
    private GraphView graph;
    private GraphView graph_roll;
    private int graphNumber = 0;
    private ArrayList<Double> buffer_Data=new ArrayList<>();
    private ArrayList<Double> roll_buffer_Data=new ArrayList<>();
    private ArrayList<Double> second_buffer_Data = new ArrayList<>();
    private ArrayList<Double> second_roll_buffer_Data = new ArrayList<>();
    private ArrayList<Integer[]> getRound_Data = new ArrayList<>();
    private ArrayList<Integer[]> second_getRound_Data = new ArrayList<>();
    int countx=0;
    public class MyBroadcaseReceiver1 extends BroadcastReceiver {

        public void addToBuffer(double result)
        {
            buffer_Data.add(result);
        }
        public void addToRollBuffer(double result)
        {
            roll_buffer_Data.add(result);
        }
        @Override
        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub
            graph_pt_pitch = intent.getDoubleExtra("first_dev_pitch", 0);
            graph_pt_roll = intent.getDoubleExtra("first_dev_roll",0);
            Log.d("grappg",graph_pt_pitch+" "+graph_pt_roll);
            if(graph_pt_pitch!=0)
                addToBuffer(graph_pt_pitch);
            if(graph_pt_roll!=0)
                addToRollBuffer(graph_pt_roll);

            //tommy change 78
            //if(sender==78)sender=0;\

        }
    }

    private Timer mTimer;
    private void setTimerTask() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                /*
                first sensor
                 */
                if(buffer_Data.size()!=0&&second_buffer_Data.size()!=0) {
                    countx++;
                    double sum = 0;
                    double sum_sec=0;
                    try {
                        for (double sum_of_data : buffer_Data) {
                            sum += sum_of_data;
                        }
                        for (double sum_of_data : second_buffer_Data) {
                            sum_sec += sum_of_data;
                        }
                    }
                    catch (Exception e)
                    {

                    }
                    final double buf = sum / buffer_Data.size();
                    final double buf_sec = sum_sec / second_buffer_Data.size();
                    Log.d("bufx1", String.valueOf(buf));
                    getRound_Data.add(new Integer[]{(int)buf,(int)buf_sec});

/*
 angle.setText(String.valueOf((int)buf));
                    currentreading += (int)buf;
 */                 runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            angle.setText(String.valueOf((int)buf));
                            currentreading += (int)buf;
                        }
                    });
                    buffer_Data.clear();
                    second_buffer_Data.clear();
                }
                /*
                second sensor
                 */


            }
        }, 1000, 500/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);
    }
    public class MyBroadcaseReceiver2 extends BroadcastReceiver {
        public void addToSecondRollBuffer(double result)
        {

            second_roll_buffer_Data.add(result);

        }
        public void addToSecondBuffer(double result)
        {

            second_buffer_Data.add(result);

        }
        @Override
        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub
            second_graph_pt_pitch = intent.getDoubleExtra("second_dev_pitch",0);
            second_graph_pt_roll = intent.getDoubleExtra("second_dev_roll",0);
            Log.d("second_graph",second_graph_pt_pitch+" "+second_graph_pt_roll);
            if(second_graph_pt_pitch!=0);
                addToSecondBuffer(second_graph_pt_pitch);
            if(second_graph_pt_roll!=0)
                addToSecondRollBuffer(second_graph_pt_roll);
            //tommy change 78
            //if(sender==78)sender=0;

        }
    }
    private Timer mTimer2;
    private void setTimerTask2() {
        mTimer2.schedule(new TimerTask() {
            @Override
            public void run() {
                /*
                first sensor
                 */
                if(second_roll_buffer_Data.size()!=0&&roll_buffer_Data.size()!=0) {

                    double sum = 0;
                    double sumofroll=0;
                    try {
                        for (double sum_of_data : roll_buffer_Data) {
                            sum += sum_of_data;
                        }
                        for (double sum_of_data : second_roll_buffer_Data) {
                            sumofroll += sum_of_data;
                        }
                    }
                    catch (Exception e)
                    {

                    }
                    final double buf = sum / roll_buffer_Data.size();
                    final double buf_sec = sumofroll / second_roll_buffer_Data.size();
                    Log.d("buf2x", String.valueOf(buf));
                    second_getRound_Data.add(new Integer[]{(int)buf,(int)buf_sec});

/*
 angle.setText(String.valueOf((int)buf));
                    currentreading += (int)buf;
 */                 runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // angle.setText(String.valueOf((int)buf));
                           // currentreading += (int)buf;
                        }
                    });
                    roll_buffer_Data.clear();
                    second_roll_buffer_Data.clear();
                }
                /*
                second sensor
                 */


            }
        }, 1000, 500/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);
    }
    private MyBroadcaseReceiver1 m_MyReceiver1;
    private MyBroadcaseReceiver2 m_MyReceiver2;
    @Override
    protected void onStart() {
        super.onStart();
        if (!isVideoCreate) {
            prefKey = new PrefKey(this);
            isVideoCreate = true;
            Log.d("playernum", "lc");
            new Thread(new Runnable() {
                public void run() {
                    createPlaylist();
                    createExerciseModellist();
                    firstClip();
                }
            }).start();


        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }


    private void roundFinished() {
        Log.d("stageScore", String.valueOf(stageScore));

        ArrayList<Integer[]> tmp=new ArrayList<>();
        ArrayList<Integer[]> tmp2 = new ArrayList<>();
        tmp.addAll(getRound_Data);
        round_list.add(tmp);
        tmp2.addAll(second_getRound_Data);
        second_round_list.add(tmp2);
        mTimer.cancel();
        mTimer2.cancel();
        getRound_Data.clear();
        second_getRound_Data.clear();
        Log.d("nodatax",String.valueOf(round_list.get(0).size()));

        totalRound.add(stageScore);
        stageScore = 0;

        //addjson to the graph represnet
        currentScore.setText(String.valueOf(stageScore));

    }

    private void prepareExoPlayerFromFileUri(Uri uri) {
        if (player != null) {
            Log.d("playerx", "gr");
            return;
        }
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        MediaSource[] mediaSources = new MediaSource[vm.size()];
        for (int i = 0; i < vm.size(); i++) {
            Uri video_uri = Uri.parse(vm.get(i).videoUrl);

            Log.d("video_x", video_uri.toString());
            mediaSources[i] = buildMediaSource(video_uri);
        }

        simpleExoPlayerView.setPlayer(player);
        ConcatenatingMediaSource cms = new ConcatenatingMediaSource(mediaSources);
        if (player != null && mediaSources != null) {

            player.addListener(new SimpleExoPlayer.DefaultEventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    Log.d("statech", String.valueOf(playbackState));
                    if (playbackState == 4 && !onFinishRepeat) {
                        onFinishRepeat = true;
                        roundFinished();
                        Log.d("plex", String.valueOf(totalRound.size()));

                        for (int x : totalRound) {
                            Log.d("roundscore", String.valueOf(x));
                        }
                        Log.d("onfinishrep", String.valueOf(onFinishRepeat));
                            /*
                            1.list of json to the graph
                            2.score to the exercise
                            3.rating
                            4.improvement(marker)
                             */
                        //tomilia:onexercisefin
                        Runnable updateImages= new Runnable() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        new FinishingExercise().execute();
                                        // call your asynctask here
                                    }
                                });
                            }
                            //////YOUR CODE HERE
                        };
                        updateImages.run();
                    }

                    Log.d("playState", String.valueOf(player.getCurrentPosition()));
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    super.onPositionDiscontinuity(reason);
                    int currentPosition = 0;
                    currentPosition = player.getCurrentWindowIndex();
                    Log.d("playState", String.valueOf(currentPosition));
                    Log.d("reaseon", String.valueOf(reason));
                    if (player.getPlaybackState() == STATE_ENDED) {
                        Log.d("finish", "vid");
                    }
                    if (isTutorMode == 1) {

                        //Finish one round
                        roundFinished();
                        Log.d("try", "sessionmode");
                    }
                }


                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                    player.setPlayWhenReady(false);
                    animationAfterExercise();
                    super.onTracksChanged(trackGroups, trackSelections);
                    Log.d("num_kf_ex", String.valueOf(num_of_exercise + " " + currentExercise));
                    if (num_of_exercise == currentExercise) {
                        finish();
                    }


                }
            });
            player.setPlayWhenReady(false);
            //player.prepare(cms);
            // player.prepare(cms);

            player.prepare(cms);
            simpleExoPlayerView.setPlayer(player);
            simpleExoPlayerView.setUseController(false);
        }


    }

    private void unregDevice() {
        try {
            unregisterReceiver(m_MyReceiver1);
            unregisterReceiver(m_MyReceiver2);
            mHandler.removeCallbacks(runGraph);
            series0.resetData(new DataPoint[]{new DataPoint(0,graph_pt_pitch)});
            series1.resetData(new DataPoint[]{new DataPoint(0,second_graph_pt_pitch)});
            series2.resetData(new DataPoint[]{new DataPoint(0,graph_pt_roll)});
            series3.resetData(new DataPoint[]{new DataPoint(0,second_graph_pt_roll)});
            mGraphTimer.interrupt();


        } catch (Exception e) {

        }
    }

    private void noSkip() {
        if (skipTutor.getVisibility() == View.VISIBLE)
            skipTutor.setVisibility(View.GONE);
    }
    private void skipAvaliable()
    {
        if(skipTutor.getVisibility()==View.GONE)
            skipTutor.setVisibility(View.VISIBLE);
        //  else if(skipTutor.getVisibility()==View.VISIBLE)
        //skipTutor.setVisibility(View.GONE);
    }

    //choser

    private void animationAfterExercise() {
        Log.d("animationaftere", String.valueOf(isTutorMode));
        if (isTutorMode == 0) {
            unregDevice();
            introMode();
            GrpahAppear();
        }
        else if (isTutorMode == 1)
        {
            pauseCounter();
            skipAvaliable();
            unregDevice();
            scoreBoardAppear();
            tutorMode();
        } else {
            noSkip();
            unregDevice();
            deviceCheck();
            scoreBoardAppear();
            GrpahAppear();
        }
    }

    private Runnable runGraph;
    private double graph2LastXValue = 5d;

    private void timerForGraph(){
        runGraph = new Runnable() {
            double counter=0;
            @Override
            public void run() {
                //series0.resetData(generateData());
                if(getRound_Data.size()!=0) {
                    series0.appendData(new DataPoint(counter, getRound_Data.get(getRound_Data.size() - 1)[0]), false, 300);
                    series1.appendData(new DataPoint(counter, getRound_Data.get(getRound_Data.size() - 1)[1]), false, 300);
                }
                if(second_getRound_Data.size()!=0) {
                    series2.appendData(new DataPoint(counter, second_getRound_Data.get(second_getRound_Data.size() - 1)[0]), false, 300);
                    series3.appendData(new DataPoint(counter, second_getRound_Data.get(second_getRound_Data.size() - 1)[1]), false, 300);
                }
                counter+=0.5;
                mHandler.postDelayed(this, 500);

            }
        };
        mHandler.post(runGraph);

/*
        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries2.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 1000);
*/
    }
    private int generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
            int f = mRand.nextInt()%40*2;
        Log.d("now data",f+"");
        return f;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }
    private void switchgraph(){


        timerForGraph();

        Log.d("createsw",String.valueOf(graphNumber));
        /*
        switch (graphNumber){
            case 1:
                graph.removeAllSeries();
                lastXPoint=2;
                createGraph();
                Log.d("create1",String.valueOf(graphNumber));
                break;
            case 2:
                graph.removeAllSeries();
                lastXPoint1=2;
                createGraph1();
                Log.d("create2",String.valueOf(graphNumber));
                break;
            case 3:
                graph.removeAllSeries();
                lastXPoint2=2;
                createGraph2();
                Log.d("create3",String.valueOf(graphNumber));
                break;
            default:
                Log.d("create4",String.valueOf(graphNumber));
                graphNumber = 0;
                break;
        }
        */

    }

    private void deviceCheck() {
        devicealert = DeviceAlert.newInstance(currentExerciseInstruction(currentExercise));

        // devicealert.setArguments();
        devicealert.show(getFragmentManager(),"Edit");

    }

    private void introSound() {

        mp = MediaPlayer.create(getApplicationContext(), R.raw.welcometocadiochallege);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                challengeSound(R.raw.challegeonevsit);
            }
        });
        mp.start();

    }

    private void challengeSound(int challenge) {
        mp = MediaPlayer.create(getApplicationContext(), challenge);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
        mp.start();
    }

    /*
    to be put into sql
     */
    private int currentExerciseSound(int currentExercise) {
        switch (currentExercise) {
            case 0:
                return R.raw.challegeonevsit;
            case 1:
                return R.raw.challegetwoplank;
            default:
                return R.raw.chal3tstable;
        }
    }

    private String currentExerciseInstruction(int currentExercise) {

        return instruction.get(currentExercise).instructionUrl;
    }

    private void congrazSound() {
        mp = MediaPlayer.create(getApplicationContext(), R.raw.welldone);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        try {
                            challengeSound(currentExerciseSound(currentExercise));
                        } catch (Exception e) {

                        }
                    }
                }, 1200);

            }
        });
        mp.start();
    }

    //intro mode
    private void introMode() {
        //play intro sound
        introSound();
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

                //challengeSound();
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                rl0.setVisibility(View.GONE);
                player.setPlayWhenReady(true);
                isTutorMode = 2;
                skipAvaliable();
            }
        }.start();
    }
    private void scoreBoardAppear()
    {
        if(scoreBoard.getVisibility()==View.GONE)
            scoreBoard.setVisibility(View.VISIBLE);
        else if(scoreBoard.getVisibility()==View.VISIBLE)
            scoreBoard.setVisibility(View.GONE);
    }
    private void GrpahAppear()
    {
        if(graph.getVisibility()==View.GONE){
            graph.setVisibility(View.VISIBLE);
        }
        else if(graph.getVisibility()==View.VISIBLE){
            graph.setVisibility(View.GONE);
        }
        if(graph_roll.getVisibility()==View.GONE){
            graph_roll.setVisibility(View.VISIBLE);
        }
        else if(graph_roll.getVisibility()==View.VISIBLE){
            graph_roll.setVisibility(View.GONE);
        }
    }
    //tutor mode
    private void tutorMode(){
        ExerciseModel currentem = exerciseModelArrayList.get(currentExercise);
        GrpahAppear();
        noSkip();
        congrazSound();
        String[] tmps = {"Well done!", "Challenge " + (currentExercise + 1) + "\n" + currentem.name};
        list_of_exer_name.add(currentem.name);
        ftv.restart();
        ftv.setTexts(tmps);
        rl0.setVisibility(View.VISIBLE);
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                // congradulationAudio
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                rl0.setVisibility(View.GONE);
                skipAvaliable();

                player.setPlayWhenReady(true);
                isTutorMode = 2;
            }

        }.start();

    }

    //player mode
    private void exerciseStarts(){

        pb.setVisibility(View.VISIBLE);
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (rl.getVisibility() == View.GONE)
                    rl.setVisibility(View.VISIBLE);

                mTextField.setText(String.valueOf(millisUntilFinished / 1000 + 1));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //tomilia
                rl.setVisibility(View.GONE);
                pb.setVisibility(View.INVISIBLE);
                player.setPlayWhenReady(true);
                /*
                the graph should be shown
                1.with moving motion
                2.with transparency
                3.marking motion per sec and into list
//GraphActicity
                 */
                Log.d("exercisehi","start");
                //graphNumber++;
                switchgraph();
                Log.d("exercisehi","start1");
                receiverOpen();
                currentExercise++;
                callAsynchronousTask();
                isTutorMode = 1;
            }

        }.start();
    }

    private void receiverOpen(){
        IntentFilter itFilter = new IntentFilter("tw.android.MY_BROADCAST1");
        registerReceiver(m_MyReceiver1, itFilter);
        IntentFilter itFilter2 = new IntentFilter("tw.android.MY_BROADCAST2");
        registerReceiver(m_MyReceiver2,itFilter2);
        mTimer = new Timer();
        setTimerTask();
        mTimer2 = new Timer();
        setTimerTask2();

        first_in_data=true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.clearVideoSurface();
        player.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregDevice();

        mTimer.cancel();
        mTimer2.cancel();
        pausePlayer();
    }

    protected void createPlaylist() {
        //video json
        Call<ResponseBody> call = userClient.getPlaylistWithid("application/x-www-form-urlencoded", "Bearer " + prefKey.getAccess_token(), 1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        //tomilia: get Profile stats
                        //jsonArray translate[0]
                        final JSONObject obj = new JSONObject(response.body().string());

                        final JSONObject item_id = new JSONObject(obj.getString("item_id"));
                        name_of_exercise = item_id.getString("name");
                        JSONArray arr = obj.getJSONArray("exp_list");
                        num_of_exercise = arr.length();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject tmp = arr.getJSONObject(i);
                            //tomilia: referal , name or id???
                            vm.add(new VideoModel(tmp.get("tut_video").toString()));
                            vm.add(new VideoModel(tmp.get("video").toString()));
                            instruction.add(new InstructionModel(tmp.get("wear_tutorial_video").toString()));
                        }
                        prepareExoPlayerFromFileUri(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/"));
                        Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //  Toast.makeText(getActivity(),response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void createExerciseModellist() {
        ExerciseModel em = new ExerciseModel("V-Sit", 9, 11, 15, 30, 2);
        exerciseModelArrayList.add(em);
        ExerciseModel em2 = new ExerciseModel("Plank", 10, 12, 20, 30, 2);
        exerciseModelArrayList.add(em2);
        ExerciseModel em3 = new ExerciseModel("T-Stabilization", 9, 11, 15, 30, 2);
        exerciseModelArrayList.add(em3);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    protected void firstClip() {
        ExerciseModel currentem = exerciseModelArrayList.get(currentExercise);
        String[] tmps = {"Welcome to Cardio challenge!", "Challenge " + (currentExercise + 1) + "\n" + currentem.name};
        list_of_exer_name.add(currentem.name);
        ftv.setTexts(tmps);
    }

    private void pausePlayer() {
        if (player != null)
            player.setPlayWhenReady(false);
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.showController();
        simpleExoPlayerView.setControllerHideOnTouch(false);
        /*
        device?
        graph?
         */
        pause.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            sis = savedInstanceState;
        }
        mTimer = new Timer();
        mTimer2 = new Timer();
        mGraphTimer= new Thread(runGraph);
        setContentView(R.layout.activity_exercise);
        pause = findViewById(R.id.exo_pause);
        play = findViewById(R.id.exo_play);
        skipTutor = findViewById(R.id.skiptut);
        rl = findViewById(R.id.timerrl);
        simpleExoPlayerView = findViewById(R.id.audio_view);
        ftv = findViewById(R.id.fadingTransition);
        mTextField = findViewById(R.id.countdown);
        pb = findViewById(R.id.video_progressbar);
        rl0 = findViewById(R.id.timerrl0);
        angle = findViewById(R.id.angle);
        scoreBoard = findViewById(R.id.scoreboard);
        currentScore = findViewById(R.id.score);
        graph = (GraphView)findViewById(R.id.graph1);
        graph_roll = (GraphView)findViewById(R.id.graph2);
        //    baseScore = findViewById(R.id.basescore);
        rl.setVisibility(View.GONE);
        Log.d("pkxt", "owow");
        m_MyReceiver1 = new MyBroadcaseReceiver1();
        m_MyReceiver2 = new MyBroadcaseReceiver2();
        //create graph frame
        createGraph();
        createGraph1();
        ela = new ExerciseListAdapter(this, temp);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setPlayWhenReady(true);
                simpleExoPlayerView.setUseController(false);
                simpleExoPlayerView.hideController();
                simpleExoPlayerView.setControllerHideOnTouch(false);
                pause.setVisibility(View.VISIBLE);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausePlayer();
            }
        });
    }



    public void onClickSkip(View v) {
        player.seekTo(player.getDuration());
    }

    private void scoreAdder() {
        stageScore++;
        currentScore.setText(String.valueOf(stageScore));
        mp = MediaPlayer.create(getApplicationContext(), R.raw.ding);
        mp.start();
    }

    public void pauseCounter() {
        if (doAsynchronousTask != null)
            doAsynchronousTask.cancel();
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            Log.d("readffromace", String.valueOf(currentExercise - 1) + " " + String.valueOf(currentreading / 40));
                            if (analyticzer.analyze(currentExercise - 1, currentreading / 40)) {
                                scoreAdder();
                            }
                            currentreading = 0;
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 50000 ms
    }

    public void onFinish() {
        player.release();
        unregDevice();
        mTimer2.cancel();
        mTimer.cancel();
        finish(); // if you want this activity to go away
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        if (mp != null)
            mp.release();
        if (doAsynchronousTask != null)
            doAsynchronousTask.cancel();
        unregDevice();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        exerciseStarts();

    }

    private void saveCurrentExercisePoint() {

    }

    private class FinishingExercise extends AsyncTask<Void, Void, String>
    {


        @Override
        protected String doInBackground(Void... voids) {

            for(Integer[] i:round_list.get(0))
            {
                Log.d("nodata",String.valueOf(i));
            }
            for(Integer[] i:second_round_list.get(0))
            {
                Log.d("secondnodata",String.valueOf(i));
            }

            Result_Maker resultMaker = new Result_Maker(getApplicationContext(), name_of_exercise, num_of_exercise, totalRound, list_of_exer_name,round_list,second_round_list);
            //temp gen

            Log.d("round:lis ",String.valueOf(round_list.size()));
            resultMaker.makeJSON();
            String id;
            do {
                id= resultMaker.getId();
            }
            while(id==null);
            Log.d("idx",id);

            return id;
        }

        protected void onPostExecute(String result)
        {

            Intent i = new Intent(ExerciseActivity.this,GraphActivity.class);
            i.putExtra("exkey",Integer.valueOf(result));
            finish();
            startActivity(i);
        }
        }
    private void addRandomDataPoint() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastXPoint++;
                series0.appendData(new DataPoint(lastXPoint,graph_pt_pitch),true,100);
                series1.appendData(new DataPoint(lastXPoint,30),true,100);
                saveCurrentExercisePoint();
                //series.appendData(new DataPoint(lastXPoint, graph_pt_pitch), false, 100);
                addRandomDataPoint();
                Log.d("create000",String.valueOf(graphNumber));
                Log.d("createA0A",String.valueOf(lastXPoint));
            }
        }, 1000);
    }
    private void addRandomDataPoint1() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastXPoint1++;
                series2.appendData(new DataPoint(lastXPoint1,graph_pt_pitch),true,100);
                series3.appendData(new DataPoint(lastXPoint1,40),true,100);
                saveCurrentExercisePoint();
                //series.appendData(new DataPoint(lastXPoint1, graph_pt_pitch), false, 100);
                addRandomDataPoint1();
                Log.d("create111",String.valueOf(graphNumber));
                Log.d("createA1A",String.valueOf(lastXPoint1));
            }
        }, 1000);
    }

    private void addRandomDataPoint2() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastXPoint2++;
                series4.appendData(new DataPoint(lastXPoint2,graph_pt_pitch),true,100);
                series5.appendData(new DataPoint(lastXPoint2,45),true,100);
                saveCurrentExercisePoint();
                //series.appendData(new DataPoint(lastXPoint2, graph_pt_pitch), false, 100);
                addRandomDataPoint2();
                Log.d("create222",String.valueOf(graphNumber));
                Log.d("createA2A",String.valueOf(lastXPoint2));
            }
        }, 1000);
    }

    public void createGraph(){
        //graph start
        Log.d("createGraph0",String.valueOf(graphNumber));

        series0 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, graph_pt_pitch),
        });

        series1 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, second_graph_pt_pitch),
        });
        //addRandomDataPoint();
        //addRandomDataPoint1();
        series0.setColor(Color.GREEN);
        series1.setColor(Color.YELLOW);


        //graph.addSeries(series1);
        //graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setMinY(0);
        //graph.getViewport().setMaxY(90);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-90);
        graph.getViewport().setMaxY(90);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);
        graph.addSeries(series0);
        graph.addSeries(series1);
        graph.setTitle("Leg Elevation");
        graph.setTitleColor(Color.WHITE);
//graph end
    }

    public void createGraph1(){


        series2 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, graph_pt_roll),
        });

        series3 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, second_graph_pt_roll),
        });
        //addRandomDataPoint();
        //addRandomDataPoint1();
        series2.setColor(Color.rgb(63, 235, 255));
        series3.setColor(Color.rgb(255, 63, 248));


        //graph.addSeries(series1);
        //graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setMinY(0);
        //graph.getViewport().setMaxY(90);
        graph_roll.getViewport().setYAxisBoundsManual(true);
        graph_roll.getViewport().setMinY(-90);
        graph_roll.getViewport().setMaxY(90);
        graph_roll.getViewport().setXAxisBoundsManual(true);
        graph_roll.getViewport().setMinX(0);
        graph_roll.getViewport().setMaxX(20);
        graph_roll.addSeries(series2);
        graph_roll.addSeries(series3);
        graph_roll.setTitle("Leg Rotation");
        graph_roll.setTitleColor(Color.WHITE);
//graph end
    }

    public void createGraph2(){
        //graph start
        Log.d("createGraph2",String.valueOf(graphNumber));

        series4 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        series5 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 45),
        });

        addRandomDataPoint2();
        //addRandomDataPoint1();
        series4.setColor(Color.RED);

        graph.addSeries(series4);
        graph.addSeries(series5);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(20);

//graph end
    }
}
