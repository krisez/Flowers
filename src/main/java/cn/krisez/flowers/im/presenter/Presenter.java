package cn.krisez.flowers.im.presenter;

import android.widget.Toast;

import java.util.List;

import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.model.ISearchModel;
import cn.krisez.flowers.im.model.SearchModel;
import cn.krisez.flowers.im.ui.ISearchView;

/**
 * Created by Krisez on 2018-03-24.
 */

public class Presenter implements IPresenter ,OnSearchListener{

    private ISearchView mView;
    private ISearchModel mModel;

    public Presenter(ISearchView view) {
        mView = view;
        mModel = new SearchModel();
    }

    @Override
    public void search() {
        mModel.search(mView.getUser(),this);
    }

    @Override
    public void successSearch(List<User> users) {
        mView.addItem(users);
    }

    @Override
    public void failed(String s) {
        mView.wrong(s);
    }
}
