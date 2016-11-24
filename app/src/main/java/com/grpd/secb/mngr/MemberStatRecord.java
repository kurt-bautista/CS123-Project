package com.grpd.secb.mngr;

import io.realm.RealmObject;

/**
 * Created by Dion Velasco on 11/24/2016.
 */

public class MemberStatRecord extends RealmObject {

    String id;
    String member_id;
    String stat_type_id;
    String stat_record_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getStat_type_id() {
        return stat_type_id;
    }

    public void setStat_type_id(String stat_type_id) {
        this.stat_type_id = stat_type_id;
    }

    public String getStat_record_id() {
        return stat_record_id;
    }

    public void setStat_record_id(String stat_record_id) {
        this.stat_record_id = stat_record_id;
    }
}
