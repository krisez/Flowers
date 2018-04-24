package cn.krisez.flowers.im.model;

import cn.krisez.flowers.im.presenter.OnSearchListener;

/**
 * Created by Krisez on 2018-03-24.
 */

public interface ISearchModel {
    void search(String username, OnSearchListener listener);
}
