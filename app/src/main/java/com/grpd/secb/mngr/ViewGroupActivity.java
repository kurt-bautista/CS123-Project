package com.grpd.secb.mngr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;

/**
 * Created by Dion Velasco on 11/21/2016.
 */

public class ViewGroupActivity extends AppCompatActivity {

    Realm realm;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group);
        TabHost tabHost = (TabHost) findViewById(R.id.TabHost);
        tabHost.setup();
        TabHost.TabSpec tabs = tabHost.newTabSpec("tab1");
        tabs.setContent(R.id.tab1);
        tabs.setIndicator("Members");
        tabHost.addTab(tabs);
        tabs = tabHost.newTabSpec("tab2");
        tabs.setContent(R.id.tab2);
        tabs.setIndicator("Stats");
        tabHost.addTab(tabs);
        Intent i = getIntent();
        String group_id = i.getStringExtra("id");

        realm = Realm.getDefaultInstance();

        group = realm.where(Group.class).equalTo("id",group_id).findFirst();

        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView descView = (TextView)findViewById(R.id.descView);
        TextView sportView = (TextView)findViewById(R.id.sportView);

        String desc = "";

        if(group.getDescription().equals("")){

            desc = "No Description.";

        }
        else{

            desc = group.getDescription();

        }

        nameView.setText(group.getName());
        descView.setText(desc);
        sportView.setText(realm.where(Sport.class).equalTo("id",group.getSport_id()).findFirst().getName());

        ListView lv = (ListView)findViewById(R.id.memberListView);
        lv.setAdapter(new MemberViewAdapter(this,realm.where(Member.class).equalTo("group_id",group.getId()).findAll()));

    }




}
