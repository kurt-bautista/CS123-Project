package com.grpd.secb.mngr;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Sean on 11/22/2016.
 */

public class NewStatDialog extends Dialog {

    public NewStatDialog(Context c){
        super(c);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_stat_dialog);
    }
}
