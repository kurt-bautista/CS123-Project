package com.grpd.secb.mngr;

import io.realm.RealmObject;

/**
 * Created by Dion Velasco on 11/24/2016.
 */

public class Stat extends RealmObject {

    String id;
    String name;
    String record;
    String member_stat_record_id;

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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getMember_stat_record_id() {
        return member_stat_record_id;
    }

    public void setMember_stat_record_id(String member_stat_record_id) {
        this.member_stat_record_id = member_stat_record_id;
    }
}
