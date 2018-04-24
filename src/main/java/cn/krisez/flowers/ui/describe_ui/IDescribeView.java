package cn.krisez.flowers.ui.describe_ui;

import java.util.List;

import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.entity.Comment;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface IDescribeView {
    Apply getApply();
    Comment getComment();
    String getObjectId();
    void setComments(List<Comment> commentList);
    void addComment(Comment comment);
    void likes(boolean b);
}
