package com.grpd.secb.mngr;

import io.realm.RealmObject;

/**
 * Created by Dion Velasco on 11/24/2016.
 */

public class StatRecord extends RealmObject {

    String id;
    String name;
    String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
