package com.grpd.secb.mngr;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kurtv on 12/6/16.
 */

public class StatType extends RealmObject {

    @PrimaryKey
    private String id;
    private String preset_name;
    private int stat_type_code;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    private String sport;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreset_name() {
        return preset_name;
    }

    public void setPreset_name(String preset_name) {
        this.preset_name = preset_name;
    }

    public int getStat_type_code() {
        return stat_type_code;
    }

    public void setStat_type_code(int stat_type_code) {
        this.stat_type_code = stat_type_code;
    }

}
