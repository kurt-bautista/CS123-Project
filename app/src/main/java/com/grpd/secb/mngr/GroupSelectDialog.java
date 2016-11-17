package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by Dion Velasco on 11/17/2016.
 */

public class GroupSelectDialog extends Dialog {

    private GroupAdapter adapter;
    private Realm realm = Realm.getDefaultInstance();
    private int index;
    private Context c;

    public GroupSelectDialog(Context c, GroupAdapter g, int index) {
        super(c);
        this.c = c;
        this.index = index;
        this.adapter = g;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.group_select_dialog);

        final TextView delGroup = (TextView) this.findViewById(R.id.delGroup);
        delGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                Group.deleteFromRealm(realm.where(Group.class).equalTo("id",adapter.getItem(index).getId()).findFirst());
                realm.commitTransaction();
                adapter.notifyDataSetChanged();
                GroupSelectDialog.this.dismiss();
            }
        });

        final TextView editGroup = (TextView) this.findViewById(R.id.editGroup);
        editGroup.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Dialog d = new NewGroupDialog(c,adapter,true,index);
                d.show();
                GroupSelectDialog.this.dismiss();
            }
        });

    }

}
