package com.grpd.secb.mngr;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

import io.realm.Realm;

public class TaekwondoStatTrackActivity extends AppCompatActivity implements View.OnClickListener{

    private Realm realm = Realm.getDefaultInstance();
    private StatSessionTracker tracker;
    private long timerSet = 60000;
    private long curMinutes = 0;
    private long curSeconds = 0;
    private int stepCount = 0;
    private boolean isActive = false;
    private boolean isDone = false;
    ImageButton startStop;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taekwondo_stat_track);

        Intent intent = getIntent();
        final String[] ids = intent.getStringArrayExtra("ids");
        String title = intent.getStringExtra("title");
        final String dataName = intent.getStringExtra("dataName");
        final String[] msrids = new String[2];
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

        Button set = (Button)findViewById(R.id.setButtonTaek);
        final Button reset = (Button)findViewById(R.id.resetButtonTaek);
        startStop = (ImageButton)findViewById(R.id.startStopButtonTaek);
        final Button counter = (Button)findViewById(R.id.counterStepsButtonTaek);
        Button cancel = (Button)findViewById(R.id.cancelButtonTaek);
        Button save = (Button)findViewById(R.id.saveButtonTaek);
        final TextView counterDisplay = (TextView)findViewById(R.id.counterStepsTextViewTaek);
        final TextView timerDisplay = (TextView) findViewById(R.id.timerTextView);
        final NumberFormat f = new DecimalFormat("00");
        timer =  new CountDownTimer(timerSet, 1000) {

            public void onTick(long millisUntilFinished) {
                curMinutes = (millisUntilFinished/1000)/60;
                curSeconds =(millisUntilFinished/1000)%60;
                timerDisplay.setText(f.format((millisUntilFinished/1000)/60)+ ":" + f.format((millisUntilFinished/1000)%60));
            }

            public void onFinish() {
                counter.setEnabled(false);
                startStop.setEnabled(false);
                isActive = false;
                startStop.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                timerDisplay.setText(f.format(0)+ ":" + f.format(0));
            }
        };

        startStop.setOnClickListener(this);

        timerDisplay.setText(f.format((timerSet / 1000) / 60) + ":" + f.format((timerSet / 1000) % 60));

        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!isActive) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(TaekwondoStatTrackActivity.this);

                    alert.setTitle("Set Timer");
                    alert.setMessage("Input Countdown length in seconds:");

                    final EditText input = new EditText(TaekwondoStatTrackActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (!input.getText().toString().equals("")) {
                                timerSet = Long.parseLong(input.getText().toString()) * 1000;

                                timerDisplay.setText(f.format((timerSet / 1000) / 60) + ":" + f.format((timerSet / 1000) % 60));
                                timer = new CountDownTimer(timerSet, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        curMinutes = (millisUntilFinished/1000)/60;
                                        curSeconds =(millisUntilFinished/1000)%60;
                                        timerDisplay.setText(f.format((millisUntilFinished / 1000) / 60) + ":" + f.format((millisUntilFinished / 1000) % 60));
                                    }

                                    public void onFinish() {
                                        counter.setEnabled(false);
                                        startStop.setEnabled(false);
                                        isActive = false;
                                        startStop.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                                        timerDisplay.setText(f.format(0)+ ":" + f.format(0));
                                    }
                                };
                            } else {

                                Toast.makeText(TaekwondoStatTrackActivity.this, "Field Empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.show();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!isActive){
                    timerDisplay.setText(f.format((timerSet/1000)/60)+ ":" + f.format((timerSet/1000)%60));
                    stepCount = 0;
                    curMinutes = 0;
                    curSeconds = 0;
                    counterDisplay.setText(Integer.toString(stepCount));
                    timer =  new CountDownTimer(timerSet, 1000) {

                        public void onTick(long millisUntilFinished) {
                            curMinutes = (millisUntilFinished/1000)/60;
                            curSeconds =(millisUntilFinished/1000)%60;
                            timerDisplay.setText(f.format((millisUntilFinished/1000)/60)+ ":" + f.format((millisUntilFinished/1000)%60));
                        }

                        public void onFinish() {
                            counter.setEnabled(false);
                            startStop.setEnabled(false);
                            isActive = false;
                            startStop.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                            timerDisplay.setText(f.format(0)+ ":" + f.format(0));
                        }
                    };
                }
            }
        });

        counter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(isActive) {
                    stepCount++;
                    counterDisplay.setText(Integer.toString(stepCount));
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tracker.deleteSession();
                TaekwondoStatTrackActivity.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for(String id:ids){

                    if(!id.equals("")){

                        long minutes, seconds;
                        if(curMinutes != 0 || curSeconds != 0){

                            minutes = ((timerSet/1000)/60)-curMinutes;
                            seconds = ((timerSet/1000)%60)-curSeconds;

                            if(seconds < 0){

                                seconds += (minutes*60);
                                minutes -= 1;

                            }

                        }
                        else{

                            minutes = ((timerSet/1000)/60);
                            seconds = ((timerSet/1000)%60);
                        }
                        tracker.addStat(dataName,counterDisplay.getText().toString() + " in " + f.format(minutes) + ":" + f.format(seconds),id);
                        System.out.println(id + ": " + counterDisplay.getText().toString() + " in " + f.format(minutes) + ":" + f.format(seconds));

                    }
                }
                isDone = true;
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(!isActive){
            Button reset = (Button)findViewById(R.id.resetButtonTaek);
            reset.callOnClick();
            timer.start();
            isActive = true;
            startStop.setImageResource(R.drawable.ic_stop_black_24dp);

        }
        else{
            timer.cancel();
            isActive = false;
            System.out.println(curMinutes + " - " + curSeconds);
            startStop.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!isDone) {
            tracker.deleteSession();
            System.out.println("---------------------STOPPED---------------------");
        }
    }
}
