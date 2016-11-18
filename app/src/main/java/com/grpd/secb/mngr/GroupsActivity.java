package com.grpd.secb.mngr;

import android.app.Activity;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class GroupsActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String[] tempArray = getResources().getStringArray(R.array.sports);
        for(String s: tempArray){

            RealmResults<Sport> set = realm.where(Sport.class).equalTo("name",s).findAll();

            if(set.size() == 0) {
                Sport sport = realm.createObject(Sport.class);
                sport.setId(UUID.randomUUID().toString());
                sport.setName(s);
            }


        }
        realm.commitTransaction();
        ListView lv = (ListView) findViewById(R.id.groupsListView);
        RealmResults<Group> groups = realm.where(Group.class).findAll();
        adapter = new GroupAdapter(this, groups);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {

                GroupSelectDialog d = new GroupSelectDialog(GroupsActivity.this, adapter, index);
                d.show();

                return true;
            }
        });
    }

    public void newGroup(View view) {
        NewGroupDialog d = new NewGroupDialog(this, adapter,false, 0);
        d.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem filter = menu.findItem(R.id.filter);
        return  super.onCreateOptionsMenu(menu);
    }
}
