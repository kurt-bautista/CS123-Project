package com.grpd.secb.mngr;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Dion Velasco on 11/21/2016.
 */

public class Member extends RealmObject {

    String id;
    String name;
    Date birthday;
    int age;
    String height;
    String weight;
    String other_description;
    String contact_number;
    String group_id;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOther_description() {
        return other_description;
    }

    public void setOther_description(String other_description) {
        this.other_description = other_description;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
