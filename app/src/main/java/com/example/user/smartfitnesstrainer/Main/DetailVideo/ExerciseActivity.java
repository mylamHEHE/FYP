package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

<<<<<<< HEAD
public class ExerciseActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    private RecyclerView rv;
    private ExerciseListAdapter ela;
    private ArrayList <String> temp = new ArrayList<>();
    private VideoView vf;
    private boolean isVideoCreate = false;
    private FadingTextView ftv;
    private ArrayList <VideoModel> vm = new ArrayList<>();
    private ImageButton pause;
    private DeviceAlert devicealert;
    private TextView mTextField;
    private int isTutorMode = 0;
    private ArrayList<ExerciseModel> exerciseModelArrayList = new ArrayList<>();
    int currentExercise = 0;
    private ProgressBar pb;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageButton play;
    private RelativeLayout rl;
    private RelativeLayout rl0;

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
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

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
                public void onPositionDiscontinuity(int reason) {
                    super.onPositionDiscontinuity(reason);
                    Log.d("gene",String.valueOf(player.getCurrentWindowIndex()));


                    player.setPlayWhenReady(false);

                        animationAfterExercise();



                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                    super.onTracksChanged(trackGroups, trackSelections);


                }
            });
            player.prepare(cms);
            simpleExoPlayerView.setUseController(false);
        }

    }
    //choser
    private void animationAfterExercise(){
        Log.d("animationaftere",String.valueOf(isTutorMode));
        if (isTutorMode==0)
        {
            introMode();

        }
        else if (isTutorMode == 1)
        {
            tutorMode();
        }
        else
        {
            deviceCheck();

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
            }
        }.start();
    }
    //tutor mode
    private void tutorMode(){
        ExerciseModel currentem = exerciseModelArrayList.get(currentExercise);
        String[] tmps = {"Tutor mode","Well done!","Challenge "+currentExercise+"\n"+currentem.name};
        ftv.setTexts(tmps);
        new CountDownTimer(3000,1000) {

            public void onTick(long millisUntilFinished) {

                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                rl0.setVisibility(View.GONE);
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
        super.onCreate(savedInstanceState);


=======
public class ExerciseActivity extends AppCompatActivity {
    RecyclerView rv;
    ExerciseListAdapter ela;
    ArrayList <String> temp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
>>>>>>> a9879dc806237699166fca393e3f914309aa1602
        setContentView(R.layout.activity_exercise);
        rv = findViewById(R.id.rv);
        temp.add("Inchworm");
        temp.add("Power Skip");
        temp.add("Uppercut");
        temp.add("Mountain Climber Twist");
        ela = new ExerciseListAdapter(this,temp);
        rv.setAdapter(ela);
        LinearLayoutManager llmm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llmm);
    }

}
