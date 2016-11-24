package com.grpd.secb.mngr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by Sean on 11/22/2016.
 */

public class NewStatDialog extends Dialog {

    private Group group;
    private Context c;
    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<Member> selectedMembers;
    private int maxCount = 2;
    private int curCount;

    public NewStatDialog(Context c, Group g){
        super(c);

        group = g;
        this.c = c;
        selectedMembers = new ArrayList<Member>();
        curCount = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_stat_dialog);

        final EditText title = (EditText)findViewById(R.id.sessionTitleEditText);
        final EditText dataName = (EditText)findViewById(R.id.sessionDataEditText);

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

                if(curCount == 0){

                    start.setEnabled(false);

                }
                else{

                    start.setEnabled(true);

                }
            }
        });
        Button start = (Button)findViewById(R.id.startButton);
        start.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText().toString().trim().equals("") ||
                        dataName.getText().toString().trim().equals("")) {

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
                        count++;
                    }

                    Intent intent = new Intent(c,TaekwondoStatTrackActivity.class);
                    intent.putExtra("ids",ids);
                    intent.putExtra("dataName",dataName.getText().toString().trim());
                    intent.putExtra("title",title.getText().toString().trim());
                    c.startActivity(intent);
                    dismiss();

                }
            }
        });



    }
}
