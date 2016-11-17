package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import io.realm.Realm;

public class NewGroupDialog extends Dialog {

    private GroupAdapter adapter;
    private boolean isEdit;
    private int index;

    public NewGroupDialog(Context c, GroupAdapter g, boolean isEdit, int index) {
        super(c);
        if(!isEdit){
            setTitle("New Group");
        }
        else{
            setTitle("Edit Group");
        }
        this.adapter = g;
        this.isEdit = isEdit;
        this.index = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group_dialog);

        final EditText name = (EditText) findViewById(R.id.newGroupNameEditText);
        final EditText description = (EditText) findViewById(R.id.newGroupDescriptionEditText);

        if(isEdit){

            Realm realm = Realm.getDefaultInstance();

            Group group = realm.where(Group.class).equalTo("id",adapter.getItem(index).getId()).findFirst();

            System.out.println(group);

            name.setText(group.getName());
            description.setText(group.getDescription());

        }

        Button confirm = (Button) findViewById(R.id.createGroupButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("No Group Name")
                            .setMessage("Please input a group name.")
                            .setNeutralButton("OK", null)
                            .show();
                }
                else {
                    Realm realm = Realm.getDefaultInstance();
                    if(isEdit){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Group g = realm.where(Group.class).equalTo("id",adapter.getItem(index).getId()).findFirst();
                                //g.setId(UUID.randomUUID().toString());
                                g.setName(name.getText().toString());
                                g.setDescription(description.getText().toString());
                            }
                        });
                    }
                    else{
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Group g = realm.createObject(Group.class, UUID.randomUUID().toString());
                                //g.setId(UUID.randomUUID().toString());
                                g.setName(name.getText().toString());
                                g.setDescription(description.getText().toString());
                            }
                        });
                    }
                    adapter.notifyDataSetChanged();
                    NewGroupDialog.this.dismiss();
                }
            }
        });
        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewGroupDialog.this.dismiss();
            }
        });
    }
}
