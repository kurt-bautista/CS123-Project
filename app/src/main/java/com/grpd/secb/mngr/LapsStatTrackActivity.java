package com.grpd.secb.mngr;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;

public class LapsStatTrackActivity extends AppCompatActivity implements View.OnClickListener{

    private Realm realm = Realm.getDefaultInstance();
    private StatSessionTracker tracker;
    private String curTime;
    private ImageButton startStop;
    private boolean isActive = false;
    private int player1Laps = 0;
    private int player2Laps = 0;
    private ArrayList<String> player1Times = new ArrayList<String>();
    private ArrayList<String> player2Times = new ArrayList<String>();
    private View[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimming_stat_track);

        Intent intent = getIntent();
        final String[] ids = intent.getStringArrayExtra("ids");
        String title = intent.getStringExtra("title");
        final String dataName = intent.getStringExtra("dataName");
        final String[] msrids = new String[2];
        views = new View[2];
        msrids[0] = ""; msrids[1] = "";
        int count = 0;
        for(String id: ids) {
            if(!id.equals("")) {
                realm.beginTransaction();
                MemberStatRecord msr = realm.createObject(MemberStatRecord.class);
                msr.setId(UUID.randomUUID().toString());
                msr.setMember_id(id);
                msrids[count] = msr.getId();
                realm.commitTransaction();
            }
            count++;
        }

        tracker = new StatSessionTracker(realm.where(MemberStatRecord.class).equalTo("id",msrids[0]).or().equalTo("id",msrids[1]).findAll(),title);

        count = 1;
        for(String id:ids){

            if(!id.equals("")){

                ViewStub stub = (ViewStub)findViewById(getResources().getIdentifier("playerStub"+count,"id",getPackageName()));
                views[count-1] = stub.inflate();

                Button lap = (Button)views[count-1].findViewById(R.id.LapButton);
                lap.setEnabled(false);
                TextView playerName = (TextView)views[count-1].findViewById(R.id.lapName);
                final TextView lapTime = (TextView)views[count-1].findViewById(R.id.timeDisplay);
                final int idCode = count;
                lapTime.setText("No laps");
                playerName.setText(realm.where(Member.class).equalTo("id",id).findFirst().getName());
                lap.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int lapCount;
                        ArrayList<String> tempList;
                        if(idCode == 1) {
                            player1Laps++;
                            lapCount = player1Laps;
                            tempList = player1Times;
                        }else{
                            player2Laps++;
                            lapCount = player2Laps;
                            tempList = player2Times;

                        }

                        if(lapCount > 1){

                            tempList.add(lapTime.getText().toString());

                        }
                        lapTime.setText("Lap " + lapCount + ": " + curTime);

                    }
                });

            }

            count++;
        }

        startStop = (ImageButton) findViewById(R.id.button7);

        Chronometer timeElapsed  = (Chronometer) findViewById(R.id.counterDisplay);

        timeElapsed.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                int ms = (int)((((time - h*3600000- m*60000)-(s*1000))/1000.0) * 100);
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                String mss = ms < 10 ? "0"+ms: ms+"";
                cArg.setText(mm+":"+ss);
                curTime = mm+":"+ss +":" + mss;
            }
        });

        startStop.setOnClickListener(this);

        Button cancel = (Button) findViewById(R.id.lapCancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tracker.deleteSession();
                finish();
            }
        });

        Button save = (Button) findViewById(R.id.lapSave);
        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int count = 0;
                for(View v: views) {
                    if(v != null) {
                        if(count == 0){

                            for(String s : player1Times){

                                tracker.addStat("Lap Time",s,ids[count]);

                            }

                        }else{

                            for(String s : player2Times){

                                tracker.addStat("Lap Time",s,ids[count]);

                            }

                        }
                    }
                    count++;
                }
                finish();
            }
        });


    }

    @Override
    public void onClick(View view) {

        Chronometer timeElapsed  = (Chronometer) findViewById(R.id.counterDisplay);

        if(!isActive){
            player1Laps = 0;
            player1Times = new ArrayList<String>();
            player2Laps = 0;
            player2Times = new ArrayList<String>();
            timeElapsed.setBase(SystemClock.elapsedRealtime());
            timeElapsed.start();
            isActive = true;
            startStop.setImageResource(R.drawable.ic_stop_black_24dp);

        }
        else{
            timeElapsed.stop();
            isActive = false;
            startStop.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }

        for(View v : views){

            if(v != null) {
                Button lap = (Button) v.findViewById(R.id.LapButton);
                lap.setEnabled(isActive);
            }

        }

    }
}
