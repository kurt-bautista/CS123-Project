package com.grpd.secb.mngr;

import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dion Velasco on 11/24/2016.
 */

public class StatSessionTracker{

    private RealmResults<MemberStatRecord> records;
    private Realm realm;
    private Calendar c;
    private String sessionTitle;
    private StatRecord statRecord;

    public StatSessionTracker(RealmResults<MemberStatRecord> records,String sessionTitle){

        this.records = records;
        realm = Realm.getDefaultInstance();
        c = Calendar.getInstance();
        this.sessionTitle = sessionTitle;
        createStatRecords();

    }

    private void createStatRecords(){

        realm.beginTransaction();
        StatRecord sr = realm.createObject(StatRecord.class);
        sr.setId(UUID.randomUUID().toString());
        sr.setName(sessionTitle);
        sr.setDate((c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
        realm.commitTransaction();

        statRecord = sr;
        for(MemberStatRecord record: records){

            realm.beginTransaction();
            record.setStat_record_id(sr.getId());
            realm.commitTransaction();

        }

    }

    public void addStat(String name, String data, String member_id){

        MemberStatRecord record = records.where().equalTo("member_id",member_id).findFirst();

        realm.beginTransaction();
        Stat stat = realm.createObject(Stat.class);
        stat.setId(UUID.randomUUID().toString());
        stat.setName(name);
        stat.setMember_stat_record_id(record.getId());
        stat.setStat_record_id(statRecord.getId());
        stat.setRecord(data);
        realm.commitTransaction();

    }

    public void deleteSession(){

        realm.beginTransaction();
        records.deleteAllFromRealm();
        realm.commitTransaction();

    }


}
