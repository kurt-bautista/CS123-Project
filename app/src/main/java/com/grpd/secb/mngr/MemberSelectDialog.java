package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import io.realm.Realm;

/**
 * Created by Dion Velasco on 11/23/2016.
 */

public class MemberSelectDialog extends Dialog {

    private MemberViewAdapter adapter;
    private Realm realm = Realm.getDefaultInstance();
    private int index;
    private Context c;

    public MemberSelectDialog(Context c, MemberViewAdapter g, int index) {
        super(c);
        this.c = c;
        this.index = index;
        this.adapter = g;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_select_dialog);

        final TextView delMember = (TextView) this.findViewById(R.id.delMember);
        delMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Group?")
                        .setMessage("Are you sure you want to delete this group?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                realm.beginTransaction();
                                Member.deleteFromRealm(realm.where(Member.class).equalTo("id",adapter.getItem(index).getId()).findFirst());
                                realm.commitTransaction();
                                adapter.notifyDataSetChanged();
                                MemberSelectDialog.this.dismiss();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        final TextView editMember = (TextView) this.findViewById(R.id.editMember);
        editMember.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Dialog d = new NewMemberDialog(c,realm.where(Group.class).equalTo("id",adapter.getItem(index).getGroup_id()).findFirst(),true,
                        realm.where(Member.class).equalTo("id",adapter.getItem(index).getId()).findFirst().getId());
                d.show();
                MemberSelectDialog.this.dismiss();
            }
        });

        final TextView viewMember = (TextView) this.findViewById(R.id.viewMember);
        viewMember.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, ViewMemberActivity.class);
                intent.putExtra("id",adapter.getItem(index).getId());
                c.startActivity(intent);
            }
        });
    }

}
