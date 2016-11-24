package com.grpd.secb.mngr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.Realm;

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
        height.setText(member.getHeight());
        weight.setText(member.getWeight());
        contact.setText(member.getContact_number());
        if(member.getOther_description().trim().equals("")){

            description.setText("No description.");

        }
        else{

            description.setText(member.getOther_description());

        }


    }
}
