package cn.krisez.flowers.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Krisez on 2018-01-29.
 */

public class User extends BmobUser {

    private String sex;
    private int type;
    private String headUrl;
    private String nickname;
    private String mOpenId;
    private BmobRelation likePost;//收藏

    public User() {
    }

    public BmobRelation getLikePost() {
        return likePost;
    }

    public void setLikePost(BmobRelation likePost) {
        this.likePost = likePost;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOpenId(String openId) {
        mOpenId = openId;
    }

    public String getOpenId() {
        return mOpenId;
    }

    @Override
    public String toString() {
        return "User{" +
                "sex='" + sex + '\'' +
                ", type=" + type +
                ", headUrl='" + headUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mOpenId='" + mOpenId + '\'' +
                '}';
    }
}
