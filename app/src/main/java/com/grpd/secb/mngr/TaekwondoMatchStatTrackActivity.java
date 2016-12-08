package com.grpd.secb.mngr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by Sean on 12/2/2016.
 */

public class TaekwondoMatchStatTrackActivity extends AppCompatActivity{


    private Realm realm = Realm.getDefaultInstance();
    private ArrayList player1stats = new ArrayList();
    private ArrayList player2stats = new ArrayList();
    private StatSessionTracker tracker;

    private int player1score = 0;
    private int player2score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taekwondo_match);

        TextView player1name = (TextView) findViewById(R.id.player1name);
        TextView player2name = (TextView) findViewById(R.id.player2name);

        Intent intent = getIntent();
        final String[] ids = intent.getStringArrayExtra("ids");
        String title = intent.getStringExtra("title");
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

        player1name.setText(realm.where(Member.class).equalTo("id",ids[0]).findFirst().getName());
        player2name.setText(realm.where(Member.class).equalTo("id",ids[1]).findFirst().getName());

        Button player1body = (Button) findViewById(R.id.player1body);
        player1body.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Body");
                updateStat();
            }
        });
        Button player1turningbody = (Button) findViewById(R.id.player1turningbody);
        player1turningbody.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Turning Body");
                updateStat();
            }
        });
        Button player1punch = (Button) findViewById(R.id.player1punch);
        player1punch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Punch");
                updateStat();
            }
        });
        Button player1head = (Button) findViewById(R.id.player1head);
        player1head.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Head");
                updateStat();
            }
        });
        Button player1turninghead = (Button) findViewById(R.id.player1turninghead);
        player1turninghead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Turning Head");
                updateStat();
            }
        });
        Button player1kyonggo = (Button) findViewById(R.id.player1kyonggo);
        player1kyonggo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Kyonggo");
                updateStat();
            }
        });
        Button player1undo = (Button) findViewById(R.id.player1undo);
        player1undo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(player1stats.size() > 0) {
                    player1stats.remove(player1stats.size() - 1);
                    updateStat();
                }
            }
        });

        Button player2body = (Button) findViewById(R.id.player2body);
        player2body.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Body");
                updateStat();
            }
        });
        Button player2turningbody = (Button) findViewById(R.id.player2turningbody);
        player2turningbody.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Turning Body");
                updateStat();
            }
        });
        Button player2punch = (Button) findViewById(R.id.player2punch);
        player2punch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Punch");
                updateStat();
            }
        });
        Button player2head = (Button) findViewById(R.id.player2head);
        player2head.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Head");
                updateStat();
            }
        });
        Button player2turninghead = (Button) findViewById(R.id.player2turninghead);
        player2turninghead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Turning Head");
                updateStat();
            }
        });
        Button player2kyonggo = (Button) findViewById(R.id.player2kyonggo);
        player2kyonggo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Kyonggo");
                updateStat();
            }
        });
        Button player2undo = (Button) findViewById(R.id.player2undo);
        player2undo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(player2stats.size() > 0) {
                    player2stats.remove(player2stats.size() - 1);
                    updateStat();
                }
            }
        });

        Button cancel = (Button)findViewById(R.id.taekMatchCancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tracker.deleteSession();
                finish();
            }
        });

        Button save = (Button)findViewById(R.id.taekMatchSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 2; i++){

                    int bodyCount = 0;
                    int turningBodyCount = 0;
                    int punchCount = 0;
                    int headCount = 0;
                    int turningHeadCount = 0;
                    int kyonggoCount = 0;

                    for(Object s:player1stats){
                        if(s == "Body") {
                            bodyCount++;
                        } else if (s == "Turning Body"){
                            turningBodyCount++;
                        } else if (s == "Punch"){
                            punchCount++;
                        } else if (s == "Head"){
                            headCount++;
                        } else if (s == "Turning Head"){
                            turningHeadCount++;
                        } else if (s == "Kyonggo"){
                            kyonggoCount++;
                        }

                    }

                    tracker.addStat("Body",Integer.toString(bodyCount),ids[i]);
                    tracker.addStat("Turning Body",Integer.toString(turningBodyCount),ids[i]);
                    tracker.addStat("Punch",Integer.toString(punchCount),ids[i]);
                    tracker.addStat("Head",Integer.toString(headCount),ids[i]);
                    tracker.addStat("Turning Head",Integer.toString(turningHeadCount),ids[i]);
                    tracker.addStat("Kyonggo",Integer.toString(kyonggoCount),ids[i]);
                    if(i == 0) {
                        tracker.addStat("Score", Integer.toString(player1score), ids[i]);
                    }
                    else{
                        tracker.addStat("Score",Integer.toString(player2score),ids[i]);
                    }


                }
                finish();
            }
        });

    }

    public void addStat(int playerNumber, String stat){
        if(playerNumber == 1) {
            player1stats.add(stat);
        } else {
            player2stats.add(stat);
        }
    }

    public void updateStat(){
        int bodyCount1 = 0;
        int turningBodyCount1 = 0;
        int punchCount1 = 0;
        int headCount1 = 0;
        int turningHeadCount1 = 0;
        int kyonggoCount1 = 0;
        int bodyCount2 = 0;
        int turningBodyCount2 = 0;
        int punchCount2 = 0;
        int headCount2 = 0;
        int turningHeadCount2 = 0;
        int kyonggoCount2 = 0;

        int player1extrapoints = 0;
        int player2extrapoints = 0;

        TextView player1statsTextView = (TextView) findViewById(R.id.player1stats);
        TextView player2statsTextView = (TextView) findViewById(R.id.player2stats);
        TextView player1scoreTextView = (TextView) findViewById(R.id.player1score);
        TextView player2scoreTextView = (TextView) findViewById(R.id.player2score);

        for(int i = 0; i < player1stats.size(); i++){
            if(player1stats.get(i) == "Body") {
                bodyCount1++;
            } else if (player1stats.get(i) == "Turning Body"){
                turningBodyCount1++;
            } else if (player1stats.get(i) == "Punch"){
                punchCount1++;
            } else if (player1stats.get(i) == "Head"){
                headCount1++;
            } else if (player1stats.get(i) == "Turning Head"){
                turningHeadCount1++;
            } else if (player1stats.get(i) == "Kyonggo"){
                kyonggoCount1++;
            }
        }
        player1statsTextView.setText("Body: "+bodyCount1+" Turning Body: "+turningBodyCount1+" Punch: "+punchCount1+"\n"+
                "Head: "+headCount1+" Turning Head: "+turningHeadCount1+" Kyonggo: "+kyonggoCount1);
        player1score = punchCount1 + bodyCount1 + (3 * turningBodyCount1) + (3 * headCount1) + (4 * turningHeadCount1);

        for(int i = 0; i < player2stats.size(); i++){
            if(player2stats.get(i) == "Body") {
                bodyCount2++;
            } else if (player2stats.get(i) == "Turning Body"){
                turningBodyCount2++;
            } else if (player2stats.get(i) == "Punch"){
                punchCount2++;
            } else if (player2stats.get(i) == "Head"){
                headCount2++;
            } else if (player2stats.get(i) == "Turning Head"){
                turningHeadCount2++;
            } else if (player2stats.get(i) == "Kyonggo"){
                kyonggoCount2++;
            }
        }
        player2statsTextView.setText("Body: "+bodyCount2+" Turning Body: "+turningBodyCount2+" Punch: "+punchCount2+"\n"+
                "Head: "+headCount2+" Turning Head: "+turningHeadCount2+" Kyonggo: "+kyonggoCount2);
        player2score = punchCount2 + bodyCount2 + (3 * turningBodyCount2) + (3 * headCount2) + (4 * turningHeadCount2);

        player1extrapoints = (int) (kyonggoCount2/2);
        player1score += player1extrapoints;
        player2extrapoints = (int) (kyonggoCount1/2);
        player2score += player2extrapoints;

        player1scoreTextView.setText("Score: "+player1score);
        player2scoreTextView.setText("Score: "+player2score);
    }
}