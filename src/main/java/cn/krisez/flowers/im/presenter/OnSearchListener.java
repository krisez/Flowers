package cn.krisez.flowers.im.presenter;

import java.util.List;

import cn.krisez.flowers.entity.User;

/**
 * Created by Krisez on 2018-03-24.
 */

public interface OnSearchListener {
    void successSearch(List<User> users);
    void failed(String s);
}
