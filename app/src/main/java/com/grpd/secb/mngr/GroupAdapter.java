package com.grpd.secb.mngr;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by kurtv on 11/15/16.
 */

public class GroupAdapter extends RealmBaseAdapter {

    private Activity context;
    private RealmResults<Group> groups;

    public GroupAdapter(@NonNull Activity context, @Nullable RealmResults<Group> groups) {
        super(context, groups);
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return groups.size();
    }


    @Nullable
    @Override
    public Group getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = context.getLayoutInflater().inflate(R.layout.group_row, null);
        TextView groupName = (TextView) v.findViewById(R.id.groupNameTextView);
        TextView groupMemberCount = (TextView) v.findViewById(R.id.memberCountTextView);

        Group group = groups.get(i);



        groupName.setText(group.getName());
        groupMemberCount.setText(Realm.getDefaultInstance().where(Member.class).equalTo("group_id",group.getId()).findAll().size() + " members");
        v.setTag(group);
        return v;
    }
}
