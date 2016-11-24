package com.grpd.secb.mngr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Dion Velasco on 11/21/2016.
 */

public class MemberViewAdapter extends RealmBaseAdapter {

    public static int VIEW_GROUP = 0;
    public static int NEW_STAT = 1;


    private RealmResults<Member> members;
    private int type;

    public MemberViewAdapter(@NonNull Context context, @Nullable RealmResults<Member> members, int type) {
        super(context, members);
        this.members = members;
        this.type = type;
    }

    @Override
    public int getCount() {
        if(type == VIEW_GROUP){

            return members.size()+1;

        }
        else{

            return members.size();

        }
    }

    @Nullable
    @Override
    public Member getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public int getPosition(Member member){

        return members.indexOf(member);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Member member = null;
        View v = inflater.inflate(R.layout.member_row, null);;
        TextView name = null;
        switch(type){

            case 0:
                if (i < members.size()){
                    member = members.get(i);
                    v = inflater.inflate(R.layout.member_row, null);
                    name = (TextView)v.findViewById(R.id.rowMemberNameTextView);
                    name.setText(member.getName());
                    v.setTag(member);
                    return v;
                }
                else {
                    v = inflater.inflate(R.layout.bottom_member_view, null);
                    v.setTag(member);
                    return v;
                }
            case 1:
                member = members.get(i);
                v = inflater.inflate(R.layout.member_row, null);
                name = (TextView)v.findViewById(R.id.rowMemberNameTextView);
                name.setText(member.getName());
                v.setTag(member);
                return v;
            default:
                member = members.get(i);
                v = inflater.inflate(R.layout.member_row, null);
                name = (TextView)v.findViewById(R.id.rowMemberNameTextView);
                name.setText(member.getName());
                v.setTag(member);
                return v;
        }

    }
}
