package com.grpd.secb.mngr;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kurtv on 11/15/16.
 */

public class Group extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
