package com.grpd.secb.mngr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Sean on 11/22/2016.
 */

public class NewStatDialog extends Dialog {

    public static final String[] Screens = {"Laps", "Match", "Timer+Counter"};
    public static HashMap<String,String> ScreenSports = new HashMap<String,String>();
    public static HashMap<String,Integer> ScreenMax = new HashMap<String,Integer>();
    public static HashMap<String,Integer> ScreenMin = new HashMap<String,Integer>();
    public static HashMap<String,String> ScreenActivity = new HashMap<String,String>();

    private Group group;
    private Activity c;
    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<Member> selectedMembers;
    private RealmList<StatType> statTypes;
    private int maxCount;
    private int minCout;
    private int curCount;
    private String curScreen = Screens[2];
    private ArrayList<String> typeList;

    public static void initValues(){

        //LapsStatTrackActivity
        ScreenSports.put(Screens[0],"Generic");
        ScreenMax.put(Screens[0],2);
        ScreenMin.put(Screens[0],1);
        ScreenActivity.put(Screens[0],"LapsStatTrackActivity");

        //TaekwondoMatchStatTrackActivity
        ScreenSports.put(Screens[1],"Taekwondo");
        ScreenMax.put(Screens[1],2);
        ScreenMin.put(Screens[1],2);
        ScreenActivity.put(Screens[1],"TaekwondoMatchStatTrackActivity");

        //TimerCounterStatTrackActivity
        ScreenSports.put(Screens[2],"Generic");
        ScreenMax.put(Screens[2],1);
        ScreenMin.put(Screens[2],1);
        ScreenActivity.put(Screens[2],"TimerCounterStatTrackActivity");

    }

    public NewStatDialog(Activity c, Group g) {
        super(c);

        group = g;
        this.c = c;

        selectedMembers = new ArrayList<Member>();
        statTypes = realm.where(Sport.class).equalTo("id", group.getSport_id()).findFirst().getStat_types();
        curCount = 0;
        maxCount = this.ScreenMax.get(curScreen);

        typeList = new ArrayList<String>();

        for(String s: Screens){

            if(ScreenSports.get(s).equals(realm.where(Sport.class).equalTo("id",g.getSport_id()).findFirst().getName()) ||
                    ScreenSports.get(s).equals("Generic")){

                typeList.add(s);

            }

        }


    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_stat_dialog);

        final EditText title = (EditText)findViewById(R.id.sessionTitleEditText);

        final Spinner statType = (Spinner) findViewById(R.id.statTrackedSpinner);
        StatTypeSpinnerAdapter statTypeAdapter = new StatTypeSpinnerAdapter(c, statTypes);
        statType.setAdapter(statTypeAdapter);

        final Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        typeSpinner.setSelection(typeList.indexOf(curScreen));
        typeSpinner.setAdapter(new TypeSpinnerAdapter(c,typeList,group));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maxCount = NewStatDialog.ScreenMax.get(adapterView.getItemAtPosition(i));
                minCout = NewStatDialog.ScreenMin.get(adapterView.getItemAtPosition(i));
                curScreen = (String)adapterView.getItemAtPosition(i);

                Button start = (Button)findViewById(R.id.startButton);

                if(curCount == 0 || curCount < minCout || curCount > maxCount){

                    start.setEnabled(false);

                }
                else {

                    start.setEnabled(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ListView lv = (ListView)findViewById(R.id.newStatMemberListView);
        lv.setAdapter(new MemberViewAdapter(c,realm.where(Member.class).equalTo("group_id",group.getId()).findAll(),MemberViewAdapter.NEW_STAT));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!selectedMembers.contains(lv.getAdapter().getItem(i)) && curCount < maxCount){

                    view.setBackgroundColor(c.getResources().getColor(R.color.colorDivider));
                    selectedMembers.add((Member)lv.getAdapter().getItem(i));
                    curCount++;
                }
                else if(selectedMembers.contains(lv.getAdapter().getItem(i))){

                    view.setBackgroundColor(Color.TRANSPARENT);
                    selectedMembers.remove(lv.getAdapter().getItem(i));
                    curCount--;
                }

                Button start = (Button)findViewById(R.id.startButton);

                if(curCount == 0 || curCount < minCout || curCount > maxCount){

                    start.setEnabled(false);

                }
                else {

                    start.setEnabled(true);

                }
            }
        });
        Button start = (Button)findViewById(R.id.startButton);
        start.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText().toString().trim().equals("")) {

                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Missing Fields")
                            .setMessage("All Fields Required")
                            .setPositiveButton("OK", null)
                            .show();
                }
                else{

                    String[] ids = new String[2];
                    ids[0] = "";ids[1] = "";

                    int count = 0;
                    for(Member member:selectedMembers){
                        ids[count] = member.getId();
                        System.out.println(member.getName());
                        count++;
                    }

                    Intent intent = new Intent(c,getScreen(curScreen));
                    intent.putExtra("ids",ids);
                    intent.putExtra("dataName",((StatType)statType.getSelectedItem()).getPreset_name());
                    //intent.putExtra("dataName","dummy");
                    intent.putExtra("title",title.getText().toString().trim());
                    c.startActivity(intent);

                    dismiss();

                }
            }
        });

    }

    private Class getScreen(String screen){

        switch (screen){
            case "Laps":return LapsStatTrackActivity.class;
            case "Match":return TaekwondoMatchStatTrackActivity.class;
            case "Timer+Counter":return TimerCounterStatTrackActivity.class;
            default: return TimerCounterStatTrackActivity.class;
        }


    }
}
