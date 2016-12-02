package com.grpd.secb.mngr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Sean on 12/2/2016.
 */

public class TaekwondoMatch extends AppCompatActivity{

    ArrayList player1stats = new ArrayList();
    ArrayList player2stats = new ArrayList();

    private int player1score = 0;
    private int player2score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taekwondo_match);

        TextView player1name = (TextView) findViewById(R.id.player1name);
        TextView player2name = (TextView) findViewById(R.id.player2name);

        //To-do: Set textview player names from realm member object

        Button player1body = (Button) findViewById(R.id.player1body);
        player1body.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Body");
                updateStat(1);
            }
        });
        Button player1turningbody = (Button) findViewById(R.id.player1turningbody);
        player1turningbody.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Turning Body");
                updateStat(1);
            }
        });
        Button player1punch = (Button) findViewById(R.id.player1punch);
        player1punch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Punch");
                updateStat(1);
            }
        });
        Button player1head = (Button) findViewById(R.id.player1head);
        player1head.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Head");
                updateStat(1);
            }
        });
        Button player1turninghead = (Button) findViewById(R.id.player1turninghead);
        player1turninghead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Turning Head");
                updateStat(1);
            }
        });
        Button player1kyonggo = (Button) findViewById(R.id.player1kyonggo);
        player1kyonggo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(1, "Kyonggo");
                updateStat(1);
            }
        });
        Button player1undo = (Button) findViewById(R.id.player1undo);
        player1undo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                player1stats.remove(player1stats.size() - 1);
                updateStat(1);
            }
        });

        Button player2body = (Button) findViewById(R.id.player2body);
        player2body.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Body");
                updateStat(2);
            }
        });
        Button player2turningbody = (Button) findViewById(R.id.player2turningbody);
        player2turningbody.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Turning Body");
                updateStat(2);
            }
        });
        Button player2punch = (Button) findViewById(R.id.player2punch);
        player2punch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Punch");
                updateStat(2);
            }
        });
        Button player2head = (Button) findViewById(R.id.player2head);
        player2head.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Head");
                updateStat(2);
            }
        });
        Button player2turninghead = (Button) findViewById(R.id.player2turninghead);
        player2turninghead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Turning Head");
                updateStat(2);
            }
        });
        Button player2kyonggo = (Button) findViewById(R.id.player2kyonggo);
        player2kyonggo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addStat(2, "Kyonggo");
                updateStat(2);
            }
        });
        Button player2undo = (Button) findViewById(R.id.player2undo);
        player2undo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                player2stats.remove(player2stats.size() - 1);
                updateStat(2);
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

    public void updateStat(int playerNumber){
        int bodyCount = 0;
        int turningBodyCount = 0;
        int punchCount = 0;
        int headCount = 0;
        int turningHeadCount = 0;
        int kyonggoCount = 0;

        int player1extrapoints = 0;
        int player2extrapoints = 0;

        TextView player1statsTextView = (TextView) findViewById(R.id.player1stats);
        TextView player2statsTextView = (TextView) findViewById(R.id.player2stats);
        TextView player1scoreTextView = (TextView) findViewById(R.id.player1score);
        TextView player2scoreTextView = (TextView) findViewById(R.id.player2score);

        if(playerNumber==1) {
            for(int i = 0; i < player1stats.size(); i++){
                if(player1stats.get(i) == "Body") {
                    bodyCount++;
                } else if (player1stats.get(i) == "Turning Body"){
                    turningBodyCount++;
                } else if (player1stats.get(i) == "Punch"){
                    punchCount++;
                } else if (player1stats.get(i) == "Head"){
                    headCount++;
                } else if (player1stats.get(i) == "Turning Head"){
                    turningHeadCount++;
                } else if (player1stats.get(i) == "Kyonggo"){
                    kyonggoCount++;
                }
            }
            player1statsTextView.setText("Body: "+bodyCount+" Turning Body: "+turningBodyCount+" Punch: "+punchCount+"\n"+
                    "Head: "+headCount+" Turning Head: "+turningHeadCount+" Kyonggo: "+kyonggoCount);
            player1score = punchCount + bodyCount + (3 * turningBodyCount) + (3 * headCount) + (4 * turningHeadCount);
            player1scoreTextView.setText("Score: "+player1score);
            player2extrapoints = (int) (kyonggoCount/2);
            player2score += player2extrapoints;
            player2scoreTextView.setText("Score: "+player2score);
        } else {
            for(int i = 0; i < player1stats.size(); i++){
                if(player1stats.get(i) == "Body") {
                    bodyCount++;
                } else if (player1stats.get(i) == "Turning Body"){
                    turningBodyCount++;
                } else if (player1stats.get(i) == "Punch"){
                    punchCount++;
                } else if (player1stats.get(i) == "Head"){
                    headCount++;
                } else if (player1stats.get(i) == "Turning Head"){
                    turningHeadCount++;
                } else if (player1stats.get(i) == "Kyonggo"){
                    kyonggoCount++;
                }
            }
            player2statsTextView.setText("Body: "+bodyCount+" Turning Body: "+turningBodyCount+" Punch: "+punchCount+"\n"+
                    "Head: "+headCount+" Turning Head: "+turningHeadCount+" Kyonggo: "+kyonggoCount);
            player2score = punchCount + bodyCount + (3 * turningBodyCount) + (3 * headCount) + (4 * turningHeadCount);
            player2scoreTextView.setText("Score: "+player2score);
            player1extrapoints = (int) (kyonggoCount/2);
            player1score += player1extrapoints;
            player1scoreTextView.setText("Score: "+player1score);
        }
    }
}