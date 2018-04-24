package cn.krisez.flowers.model.describe;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.entity.Comment;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.presenter.describe_presenter.DListener;

/**
 * Created by Krisez on 2018-03-18.
 */

public class DescribeModel implements IDescribeModel {
    /**
     * @param objectId 申请的id
     * @param listener 回调
     */
    @Override
    public void getComments(String objectId, final DListener listener) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("mApply", objectId);
        query.include("author");
        query.setLimit(3);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    listener.successList(list);
                } else listener.failed(e.getMessage());
            }
        });
    }

    @Override
    public void submit(final Comment comment, final DListener listener) {
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    listener.successSubmit(comment);
                } else listener.failed(e.getMessage());
            }
        });
    }

    /**
     * @param apply    申请详情
     * @param listener 回调
     */
    @Override
    public void likes(final Apply apply, final DListener listener) {
        if (App.getUser() != null) {
            BmobRelation relation = new BmobRelation();
            relation.add(App.getUser());
            apply.setLikes(relation);
            apply.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        setUserLike(apply, listener);
                    } else {
                        listener.failed(e.getMessage());
                    }
                }
            });
        }
    }

    private void setUserLike(final Apply apply, final DListener listener) {
        User user = App.getUser();
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(apply);
        user.setLikePost(bmobRelation);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.successLike();
                } else {
                    listener.failed(e.getMessage());
                }
            }
        });

    }

    @Override
    public void isLike(Apply apply, final DListener listener) {
        if (App.getUser() != null) {
            BmobQuery<User> query = new BmobQuery<>();
            query.addWhereRelatedTo("mLikes", new BmobPointer(apply));
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getObjectId().equals(App.getUser().getObjectId())) {
                                listener.successLike();
                                break;
                            }
                        }
                    }
                }
            });
        }

    }
}
