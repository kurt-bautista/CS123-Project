package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sean on 11/22/2016.
 */

public class NewMemberDialog extends Dialog{
    public NewMemberDialog(Context c) {
        super(c);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_member_dialog);

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewMemberDialog.this.dismiss();
            }
        });
    }
}
