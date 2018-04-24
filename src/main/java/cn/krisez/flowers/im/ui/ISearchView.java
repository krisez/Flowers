package cn.krisez.flowers.im.ui;

import java.util.List;

import cn.krisez.flowers.entity.User;

/**
 * Created by Krisez on 2018-03-24.
 */

public interface ISearchView {
    String getUser();
    void addItem(List<User> users);
    void refresh();
    void wrong(String s);
}
