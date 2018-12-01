package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
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

import com.example.user.smartfitnesstrainer.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
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
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ExerciseActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    private RecyclerView rv;
    private ExerciseListAdapter ela;
    private ArrayList <String> temp = new ArrayList<>();
    private VideoView vf;
    private boolean isVideoCreate = false;
    private Analyticzer analyticzer =new Analyticzer();
    private FadingTextView ftv;
    private ArrayList <VideoModel> vm = new ArrayList<>();
    private ImageButton pause;
    private DeviceAlert devicealert;
    private TextView mTextField;
    private int isTutorMode = 0;
    private ArrayList<ExerciseModel> exerciseModelArrayList = new ArrayList<>();
    int currentExercise = 0;
    private ProgressBar pb;
    private int stageScore =0;
    private TextView currentScore;
    private TextView baseScore;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageButton play;
    private RelativeLayout rl;
    private RelativeLayout rl0;
    private LinearLayout scoreBoard;
    private Button skipTutor;
    private Bundle sis;
    private TimerTask doAsynchronousTask;
    private double currentreading=0.0;
    public class MyBroadcaseReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            final int sender = intent.getIntExtra("sender_name",0);
            Log.d("shb",String.valueOf(sender));
            currentreading+=sender;



        }

    }
    private MyBroadcaseReceiver1 m_MyReceiver1;
    @Override
    protected void onStart() {
        super.onStart();
        if (!isVideoCreate) {
            isVideoCreate =true;
            Log.d("playernum","lc");
            createPlaylist();
            createExerciseModellist();
            firstClip();
            prepareExoPlayerFromFileUri(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.video));
        }

    }
    private MediaSource buildMediaSource(int uri){
        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(uri));
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getApplicationContext());
        try {
            rawResourceDataSource.open(dataSpec);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return rawResourceDataSource;
            }
        };
        MediaSource audioSource =
                new ExtractorMediaSource(rawResourceDataSource.getUri(),factory, new DefaultExtractorsFactory(), null, null);
        return audioSource;
    }

    private void prepareExoPlayerFromFileUri(Uri uri) {
        if (player!=null)
        {
            Log.d("playerx","gr");
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
        for (int i=0;i<vm.size();i++) {
           mediaSources[i]= buildMediaSource(vm.get(i).videoUrl);
        }

        simpleExoPlayerView.setPlayer(player);
        ConcatenatingMediaSource cms = new ConcatenatingMediaSource(mediaSources);
        if(player!=null && mediaSources!=null){

            player.addListener(new SimpleExoPlayer.DefaultEventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                    Log.d("playState",String.valueOf(player.getCurrentPosition()));
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    super.onPositionDiscontinuity(reason);
                    int currentPosition = 0;
                    currentPosition = player.getCurrentWindowIndex();
                    Log.d("playState",String.valueOf(currentPosition));








                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                    player.setPlayWhenReady(false);
                    animationAfterExercise();
                    super.onTracksChanged(trackGroups, trackSelections);


                }
            });
            player.setPlayWhenReady(false);
            //player.prepare(cms);
            player.setVolume(0.0f);
            simpleExoPlayerView.getPlayer().prepare(cms);
            simpleExoPlayerView.setUseController(false);
        }


    }
    private void unregDevice(){
        try {
            unregisterReceiver(m_MyReceiver1);
        }
        catch (Exception e )
        {

        }
    }
    private void noSkip()
    {
        if(skipTutor.getVisibility()==View.VISIBLE)
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
    private void animationAfterExercise(){
        Log.d("animationaftere",String.valueOf(isTutorMode));
        if (isTutorMode==0)
        {

            unregDevice();

            introMode();

        }
        else if (isTutorMode == 1)
        {
            pauseCounter();
            scoreBoardAppear();
            skipAvaliable();
            unregisterReceiver(m_MyReceiver1);
            tutorMode();
        }
        else
        {
            noSkip();
            unregDevice();
            deviceCheck();
            scoreBoardAppear();
        }
    }
    private void deviceCheck(){
        devicealert = new DeviceAlert();
        devicealert.show(getFragmentManager(),"Edit");
    }
    //intro mode
    private void introMode(){
        ExerciseModel currentem = exerciseModelArrayList.get(currentExercise);
        String[] tmps = {"Welcome to Cardio power","Challenge "+currentExercise+"\n"+currentem.name};
        ftv.setTexts(tmps);
        new CountDownTimer(3000,1000) {

            public void onTick(long millisUntilFinished) {
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
    //tutor mode
    private void tutorMode(){

        ExerciseModel currentem = exerciseModelArrayList.get(currentExercise);
        rl0.setVisibility(View.VISIBLE);
        noSkip();
        String[] tmps = {"Well done!","Challenge "+currentExercise+"\n"+currentem.name};
        ftv.setTexts(tmps);
        new CountDownTimer(6000,1000) {

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
                    if (millisUntilFinished>1000)
                    mTextField.setText(String.valueOf(millisUntilFinished / 1000));
                    else
                        mTextField.setText(String.valueOf(1));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

                rl.setVisibility(View.GONE);
                pb.setVisibility(View.INVISIBLE);
                player.setPlayWhenReady(true);
                currentExercise++;
                isTutorMode = 1;
            }

        }.start();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.clearVideoSurface();
        player.release();
    }
    protected void createPlaylist(){
        VideoModel vid = new VideoModel(R.raw.c1);
        vm.add(vid);
        VideoModel vid2 = new VideoModel(R.raw.c1);
        vm.add(vid2);
        VideoModel vid3 = new VideoModel(R.raw.vsit);
        vm.add(vid3);
        VideoModel vid4 = new VideoModel(R.raw.vsit);
        vm.add(vid4);
    }
    protected void createExerciseModellist(){
        ExerciseModel em = new ExerciseModel("V-Sit",9,11,15,30,2);
        exerciseModelArrayList.add(em);
        ExerciseModel em2 = new ExerciseModel("Inchworm",10,12,20,30,2);
        exerciseModelArrayList.add(em2);
        ExerciseModel em3 = new ExerciseModel("V-Sit",9,11,15,30,2);
        exerciseModelArrayList.add(em3);
        ExerciseModel em4 = new ExerciseModel("V-Sit",9,11,15,30,2);
        exerciseModelArrayList.add(em4);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    protected void firstClip(){
        ExerciseModel currentem = exerciseModelArrayList.get(currentExercise);
        String[] tmps = {"Welcome to Cardio power set!","Challenge "+currentExercise+"\n"+currentem.name};
        ftv.setTexts(tmps);

        animationAfterExercise();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sis=savedInstanceState;
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            Log.d("savce",savedInstanceState.toString());
        }

        setContentView(R.layout.activity_exercise);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        pause = findViewById(R.id.exo_pause);
        play = findViewById(R.id.exo_play);
        skipTutor = findViewById(R.id.skiptut);
        rl = findViewById(R.id.timerrl);
        simpleExoPlayerView = findViewById(R.id.audio_view);
        ftv = findViewById(R.id.fadingTransition);
        mTextField = findViewById(R.id.countdown);
        pb = findViewById(R.id.video_progressbar);
        rl0 = findViewById(R.id.timerrl0);
        scoreBoard = findViewById(R.id.scoreboard);
        currentScore = findViewById(R.id.score);
        baseScore = findViewById(R.id.basescore);
        rl.setVisibility(View.GONE);
        Log.d("pkxt","owow");
        m_MyReceiver1 = new MyBroadcaseReceiver1();


        ela = new ExerciseListAdapter(this,temp);
        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                player.setPlayWhenReady(true);
                simpleExoPlayerView.setUseController(false);
                simpleExoPlayerView.hideController();
                simpleExoPlayerView.setControllerHideOnTouch(false);
                pause.setVisibility(View.VISIBLE);
            }
        });

        pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                player.setPlayWhenReady(false);
                simpleExoPlayerView.setUseController(true);
                simpleExoPlayerView.showController();
                simpleExoPlayerView.setControllerHideOnTouch(false);
                pause.setVisibility(View.INVISIBLE);
            }
        });

        // initializePlayer();
       /* MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(vf);
        vf.setMediaController(mediaController);
*/
      /*  rv.setAdapter(ela);
        LinearLayoutManager llmm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llmm);
        */
        /*Uri uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.raw.video);
        vf.setVideoURI(uri);
        */
        /*vf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!bVideoIsBeingTouched) {
                    bVideoIsBeingTouched = true;
                    if (videoview.isPlaying()) {
                        onPause(videoview);
                    } else {
                        Log.d("vid",String.valueOf(videoview.isPlaying()));
                        onResume(videoview);
                    }

                    bVideoIsBeingTouched = false;

                }
                return true;
            }
        });vf.start();*/

    }

    public void onClickSkip(View v)
    {
        Toast.makeText(this, String.valueOf(player.getDuration()), Toast.LENGTH_LONG).show();
        player.seekTo(player.getDuration());
    }

    private void scoreAdder()
    {
        stageScore++;
        currentScore.setText(String.valueOf(stageScore));
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.ding);
        mp.start();
    }
    public void pauseCounter(){
        if(doAsynchronousTask!=null)
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
                            Log.d("readffromace",String.valueOf(currentreading)+" "+String.valueOf(currentreading/30));
                            if(analyticzer.analyze(0,currentreading/200)){
                               scoreAdder();
                            }
                         currentreading=0;

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 50000 ms
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        if(doAsynchronousTask!=null)
        doAsynchronousTask.cancel();
        unregDevice();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        exerciseStarts();
        callAsynchronousTask();
        IntentFilter itFilter = new IntentFilter("tw.android.MY_BROADCAST1");
        registerReceiver(m_MyReceiver1, itFilter);
    }
}
