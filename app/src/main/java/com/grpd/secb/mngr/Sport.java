package com.grpd.secb.mngr;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Dion Velasco on 11/18/2016.
 */

public class Sport extends RealmObject {

    private String id;
    private String name;
    private RealmList<StatType> stat_types;

    public RealmList<StatType> getStat_types() {
        return stat_types;
    }

    public void addStatType(StatType s) {
        stat_types.add(s);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
