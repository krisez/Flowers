package cn.krisez.flowers.entity;

/**
 * Created by Krisez on 2017-12-12.
 */

public class QQInfo {
    private String nickname;
    private String gender;
    private String figureurl_2;//头像地址

    public QQInfo() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }
}
