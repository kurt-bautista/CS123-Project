package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import io.realm.Realm;

public class NewGroupDialog extends Dialog {

    private GroupAdapter adapter;

    public NewGroupDialog(Context c, GroupAdapter g) {
        super(c);
        setTitle("New Group");
        this.adapter = g;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group_dialog);

        final EditText name = (EditText) findViewById(R.id.newGroupNameEditText);
        final EditText description = (EditText) findViewById(R.id.newGroupDescriptionEditText);
        Button confirm = (Button) findViewById(R.id.createGroupButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Group g = realm.createObject(Group.class, UUID.randomUUID().toString());
                        //g.setId(UUID.randomUUID().toString());
                        g.setName(name.getText().toString());
                        g.setDescription(description.getText().toString());
                    }
                });
                adapter.notifyDataSetChanged();
                NewGroupDialog.this.dismiss();
            }
        });
    }
}
