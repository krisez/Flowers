package cn.krisez.flowers.model.describe;

import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.entity.Comment;
import cn.krisez.flowers.presenter.describe_presenter.DListener;
import cn.krisez.flowers.presenter.describe_presenter.DescribePresenter;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface IDescribeModel {
    void getComments(String objectId,DListener listener);
    void submit(Comment comment, DListener listener);
    void likes(Apply apply, DListener listener);
    void isLike(Apply apply, DListener listener);
}
