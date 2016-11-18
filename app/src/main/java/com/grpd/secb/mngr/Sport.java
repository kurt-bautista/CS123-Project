package com.grpd.secb.mngr;

import io.realm.RealmObject;

/**
 * Created by Dion Velasco on 11/18/2016.
 */

public class Sport extends RealmObject {

    private String id;
    private String name;

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
