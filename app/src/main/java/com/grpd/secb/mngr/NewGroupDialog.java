package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewGroupDialog extends Dialog {

    public NewGroupDialog(Context c) {
        super(c);
        setTitle("New Group");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group_dialog);

        EditText name = (EditText) findViewById(R.id.newGroupNameEditText);
        EditText description = (EditText) findViewById(R.id.newGroupDescriptionEditText);
        Button confirm = (Button) findViewById(R.id.createGroupButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
