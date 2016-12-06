package com.grpd.secb.mngr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ViewMemberActivity extends AppCompatActivity {

    Realm realm;
    Member member;

    public ViewMemberActivity(){

        this.realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member);
        TabHost tabHost = (TabHost) findViewById(R.id.TabHost);
        tabHost.setup();
        TabHost.TabSpec tabs = tabHost.newTabSpec("tab1");
        tabs.setContent(R.id.tab1);
        tabs.setIndicator("Info");
        tabHost.addTab(tabs);
        tabs = tabHost.newTabSpec("tab2");
        tabs.setContent(R.id.tab2);
        tabs.setIndicator("Stats");
        tabHost.addTab(tabs);

        member = realm.where(Member.class).equalTo("id",getIntent().getStringExtra("id")).findFirst();

        TextView name = (TextView)findViewById(R.id.memberNameTextView);
        TextView birthday = (TextView)findViewById(R.id.birthdayTextView);
        TextView age = (TextView)findViewById(R.id.viewAgeTextView);
        TextView height = (TextView)findViewById(R.id.viewHeightTextView);
        TextView weight = (TextView)findViewById(R.id.weightTextView);
        TextView description = (TextView)findViewById(R.id.descriptionTextView);
        TextView contact = (TextView)findViewById(R.id.contactTextView);

        name.setText(member.getName());
        birthday.setText(member.getBirthday());
        age.setText(Integer.toString(member.getAge()));
        height.setText(member.getHeight() + " cm.");
        weight.setText(member.getWeight() + " kg.");
        contact.setText(member.getContact_number());
        if(member.getOther_description().trim().equals("")){

            description.setText("No description.");

        }
        else{

            description.setText(member.getOther_description());

        }

        ArrayList<RealmResults<MemberStatRecord>> msrList = new ArrayList<>();
        ArrayList<RealmResults<StatRecord>> srList = new ArrayList<>();

        msrList.add(realm.where(MemberStatRecord.class).equalTo("member_id",member.getId()).findAll());


        for(RealmResults<MemberStatRecord> resultList : msrList){

            for(MemberStatRecord result : resultList){

                srList.add(realm.where(StatRecord.class).equalTo("id",result.getStat_record_id()).findAll());

            }

        }

        RealmList<StatRecord> statRecords = new RealmList<>();

        for(RealmResults<StatRecord> srResults : srList){

            for(StatRecord result : srResults){

                if(!statRecords.contains(result)){

                    statRecords.add(result);

                }
            }

        }

        final ExpandableListView elv = (ExpandableListView)findViewById(R.id.viewMemberStatListView);
        final StatRecordAdapter adapter = new StatRecordAdapter(this,statRecords);
        elv.setAdapter(adapter);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String s) {
                adapter.notifyDataSetChanged();
                elv.invalidateViews();
            }
        });

    }
}
