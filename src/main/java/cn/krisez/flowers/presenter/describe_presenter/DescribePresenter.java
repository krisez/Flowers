package cn.krisez.flowers.presenter.describe_presenter;

import java.util.List;

import cn.krisez.flowers.entity.Comment;
import cn.krisez.flowers.model.describe.DescribeModel;
import cn.krisez.flowers.model.describe.IDescribeModel;
import cn.krisez.flowers.ui.describe_ui.IDescribeView;

/**
 * Created by Krisez on 2018-03-18.
 */

public class DescribePresenter implements IDescribePresenter ,DListener{

    private IDescribeView mView;
    private IDescribeModel mModel;

    public DescribePresenter(IDescribeView view) {
        mView = view;
        mModel = new DescribeModel();
    }

    @Override
    public void getLists() {
        mModel.getComments(mView.getObjectId(),this);
    }

    @Override
    public void submit() {
        mModel.submit(mView.getComment(),this);
    }

    @Override
    public void agree(String id) {

    }

    //收藏案件
    @Override
    public void likes() {
        mModel.likes(mView.getApply(),this);
    }

    @Override
    public void isLike() {
        mModel.isLike(mView.getApply(),this);
    }

    @Override
    public void successList(List<Comment> commentList) {
        mView.setComments(commentList);
    }

    @Override
    public void successSubmit(Comment comment) {
        mView.addComment(comment);
    }

    @Override
    public void failed(String s) {

    }

    @Override
    public void successLike() {
        mView.likes(true);
    }
}
