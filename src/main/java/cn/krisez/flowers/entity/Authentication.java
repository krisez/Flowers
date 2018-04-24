package cn.krisez.flowers.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Krisez on 2018-03-23.
 */

public class Authentication extends BmobObject{
    private String name;
    private String last;
    private User who;

    public Authentication() {
    }

    public Authentication(String name, String last, User who) {
        this.name = name;
        this.last = last;
        this.who = who;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public User getWho() {
        return who;
    }

    public void setWho(User who) {
        this.who = who;
    }
}
