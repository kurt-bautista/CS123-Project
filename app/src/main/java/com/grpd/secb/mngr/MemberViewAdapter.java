package com.grpd.secb.mngr;

import android.app.Activity;
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

    private Activity context;
    private RealmResults<Member> members;

    public MemberViewAdapter(@NonNull Activity context, @Nullable RealmResults<Member> members) {
        super(context, members);
        this.context = context;
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size()+1;
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

        if (i < members.size()){
            member = members.get(i);
            View v = inflater.inflate(R.layout.member_row, null);
            TextView name = (TextView)v.findViewById(R.id.rowMemberNameTextView);
            name.setText(member.getName());
            v.setTag(member);
            return v;
        }
        else {
            View v = inflater.inflate(R.layout.bottom_member_view, null);
            ImageView img = (ImageView)context.findViewById(R.id.imageView3);
            v.setTag(member);
            return v;
        }

    }
}
