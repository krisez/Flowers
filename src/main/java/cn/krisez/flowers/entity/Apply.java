package cn.krisez.flowers.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Krisez on 2018-03-18.
 */

public class Apply extends BmobObject {
    private String person;
    private String type;
    private String describe;
    private User author;
    private String where;
    private boolean deal;
    private boolean over;
    private BmobRelation mLikes;

    public Apply() {
    }

    public Apply(String person, String type, String describe, User author, String where, boolean deal, boolean over) {
        this.person = person;
        this.type = type;
        this.describe = describe;
        this.author = author;
        this.where = where;
        this.deal = deal;
        this.over = over;
    }

    public BmobRelation getLikes() {
        return mLikes;
    }

    public void setLikes(BmobRelation likes) {
        mLikes = likes;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


}
