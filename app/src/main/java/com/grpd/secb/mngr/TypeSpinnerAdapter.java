package com.grpd.secb.mngr;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;

/**
 * Created by Dion Velasco on 12/7/2016.
 */

public class TypeSpinnerAdapter extends BaseAdapter {


    private Activity context;
    private Group g;
    private ArrayList<String> screens;

    public TypeSpinnerAdapter(@NonNull Activity context,ArrayList<String> screens, Group g) {
        super();
        this.context = context;
        this.g = g;
        this.screens = screens;
    }

    @Override
    public String getItem(int i) {
        return screens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return screens.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = context.getLayoutInflater().inflate(R.layout.sport_spinner_row, null);

        TextView TypeName = (TextView)v.findViewById(R.id.sportName);

        if(i%2 == 1){

            TypeName.setBackgroundColor(v.getResources().getColor(R.color.colorDivider));

        }

        TypeName.setText(screens.get(i));
        v.setTag(screens.get(i));
        return v;

    }

}
