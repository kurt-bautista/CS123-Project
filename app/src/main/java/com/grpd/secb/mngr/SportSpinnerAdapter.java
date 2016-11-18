package com.grpd.secb.mngr;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Dion Velasco on 11/18/2016.
 */

public class SportSpinnerAdapter extends RealmBaseAdapter {

    private Context context;
    private RealmResults<Sport> sports;

    public SportSpinnerAdapter(@NonNull Context context, @Nullable RealmResults<Sport> sports) {
        super(context, sports);
        this.context = context;
        this.sports = sports;
    }

    @Override
    public int getCount() {
        return sports.size();
    }

    @Nullable
    @Override
    public Sport getItem(int position) {
        return sports.get(position);
    }

    public int getPosition(Sport s){

        return sports.indexOf(s);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.sport_spinner_row, null);

        TextView sportName = (TextView)v.findViewById(R.id.sportName);

        if(i%2 == 1){

            sportName.setBackgroundColor(v.getResources().getColor(R.color.colorDivider));

        }

        Sport sport = sports.get(i);
        sportName.setText(sport.getName());
        v.setTag(sport);
        return v;
    }

}
