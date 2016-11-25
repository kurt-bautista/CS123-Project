package com.grpd.secb.mngr;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Dion Velasco on 11/25/2016.
 */

public class StatRecordAdapter extends BaseExpandableListAdapter {

    private RealmList<StatRecord> records;
    private Realm realm = Realm.getDefaultInstance();
    private Activity context;

    public StatRecordAdapter(Activity context, RealmList<StatRecord> records){

        this.records = records;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return records.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return realm.where(Stat.class).equalTo("stat_record_id",records.get(i).getId()).findAll().size();
    }

    @Override
    public Object getGroup(int i) {
        return records.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return realm.where(Stat.class).equalTo("stat_record_id",records.get(i).getId()).findAll().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View v = context.getLayoutInflater().inflate(R.layout.stat_parent,null);

        StatRecord sr = records.get(i);

        TextView name = (TextView)v.findViewById(R.id.rowStatRecordNameTextView);
        TextView date = (TextView)v.findViewById(R.id.rowStatRecordDateTextView);

        name.setText(sr.getName());
        date.setText(sr.getDate());
        v.setTag(sr.getId());
        return v;
    }

    public RealmList<StatRecord> getData(){

        return records;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        View v = context.getLayoutInflater().inflate(R.layout.stat_child,null);

        Stat s = realm.where(Stat.class).equalTo("stat_record_id",records.get(i).getId()).findAll().get(i1);
        MemberStatRecord msr = realm.where(MemberStatRecord.class).equalTo("id",s.getMember_stat_record_id()).findFirst();
        Member mem = realm.where(Member.class).equalTo("id",msr.getMember_id()).findFirst();

        TextView name = (TextView)v.findViewById(R.id.rowStatNameTextView);
        TextView member = (TextView)v.findViewById(R.id.rowStatMemberTextView);
        TextView value = (TextView)v.findViewById(R.id.rowStatValueTextView);

        System.out.println(s.getRecord());

        name.setText(s.getName());
        member.setText(mem.getName());
        value.setText(s.getRecord());
        v.setTag(s.getId());
        return v;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
