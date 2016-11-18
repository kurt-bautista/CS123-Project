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
    private String sport_id;

    public String getId() {
        return id;
    }

    public String getSport_id() {
        return sport_id;
    }

    public void setSport_id(String sport_id) {
        this.sport_id = sport_id;
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
