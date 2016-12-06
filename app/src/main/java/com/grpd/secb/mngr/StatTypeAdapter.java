package com.grpd.secb.mngr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by kurtv on 12/6/16.
 */

public class StatTypeAdapter extends RealmBaseAdapter {

    private Context context;
    private RealmList<StatType> statTypes;

    public StatTypeAdapter(@NonNull Context context, @Nullable RealmList<StatType> statTypes) {
        super(context, statTypes);
        this.context = context;
        this.statTypes = statTypes;
    }

    @Override
    public int getCount() {
        return statTypes.size();
    }

    @Nullable
    @Override
    public StatType getItem(int position) {
        return statTypes.get(position);
    }

    public int getPosition(StatType s){

        return statTypes.indexOf(s);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.sport_spinner_row, null);

        TextView StatTypeName = (TextView)v.findViewById(R.id.sportName);

        if(i%2 == 1){

            StatTypeName.setBackgroundColor(v.getResources().getColor(R.color.colorDivider));

        }

        StatType s = statTypes.get(i);
        StatTypeName.setText(s.getPreset_name());
        v.setTag(s);
        return v;
    }
}
