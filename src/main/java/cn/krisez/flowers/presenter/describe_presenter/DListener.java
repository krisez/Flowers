package cn.krisez.flowers.presenter.describe_presenter;

import java.util.List;

import cn.krisez.flowers.entity.Comment;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface DListener {
    void successList(List<Comment> commentList);
    void successSubmit(Comment comment);
    void failed(String s);
    void successLike();
}
