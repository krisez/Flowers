package cn.krisez.flowers.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Krisez on 2018-03-18.
 */

public class Comment extends BmobObject {
    private String content;
    private Apply mApply ;//对应的申请;
    private User author; //对应的人
    private int count;//赞同的数量0-2

    public Comment() {
    }

    public Comment(String content, Apply apply, User author, int count) {
        this.content = content;
        mApply = apply;
        this.author = author;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Apply getApply() {
        return mApply;
    }

    public void setApply(Apply apply) {
        mApply = apply;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
