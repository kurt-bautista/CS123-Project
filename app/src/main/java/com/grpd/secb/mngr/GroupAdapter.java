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

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by kurtv on 11/15/16.
 */

public class GroupAdapter extends RealmBaseAdapter {

    private Activity context;
    private RealmResults<Group> groups;
    private final GroupAdapter me;

    public GroupAdapter(@NonNull Activity context, @Nullable RealmResults<Group> groups) {
        super(context, groups);
        this.context = context;
        this.groups = groups;
        me = this;
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

        final int index = i;
        final Group group = groups.get(i);
        v.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), ViewGroupActivity.class);
                i.putExtra("id",group.getId());
                context.startActivity(i);
            }
        });

        v.setOnLongClickListener(new AdapterView.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {

                System.out.println(context.getApplicationContext());
                System.out.println(me);
                System.out.println(index);

                GroupSelectDialog d = new GroupSelectDialog(context, me, index);
                d.show();

                return true;
            }
        });


        groupName.setText(group.getName());
        v.setTag(group);
        return v;
    }
}
