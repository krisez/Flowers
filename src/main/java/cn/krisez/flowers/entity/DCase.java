package cn.krisez.flowers.entity;

import java.io.Serializable;

/**
 * Created by Krisez on 2017/7/27.
 * 典型案例
 */

public class DCase implements Serializable {
    private String author;
    private String category;
    private String content;
    private String title;
    private String id;
    private String count;//评论数
    private String time;

    public DCase(String id, String title, String content, String category, String count, String time) {
        this.category = category;
        this.content = content;
        this.title = title;
        this.id = id;
        this.count = count;
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
