package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

        final ListView lv = (ListView)findViewById(R.id.memberListView);
        lv.setAdapter(new MemberViewAdapter(this,realm.where(Member.class).equalTo("group_id",group.getId()).findAll(),MemberViewAdapter.VIEW_GROUP));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == lv.getAdapter().getCount()-1){

                    NewMemberDialog d = new NewMemberDialog(ViewGroupActivity.this,group,false,"");
                    d.show();

                }
                else{

                    Intent intent = new Intent(ViewGroupActivity.this,ViewMemberActivity.class);
                    intent.putExtra("id",((MemberViewAdapter)lv.getAdapter()).getItem(i).getId());
                    startActivity(intent);
                }

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialog d = new MemberSelectDialog(ViewGroupActivity.this,(MemberViewAdapter)lv.getAdapter(),i);
                d.show();
                return true;
            }
        });

        Button newStat = (Button)findViewById(R.id.newStatButton);
        newStat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Dialog d = new NewStatDialog(ViewGroupActivity.this,group);
                d.show();

            }
        });

    }




}
